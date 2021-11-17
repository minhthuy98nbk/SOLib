package zingplay;

import com.google.gson.Gson;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import zingplay.data.Offer;
import zingplay.data.UserDataCustom;
import zingplay.data.UserOffers;
import zingplay.log.Logger;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SOKafkaConsumer implements Runnable{
    private static final Gson gson = new Gson();
    Consumer<String, String> consumer;
    ExecutorService executorService;

    String topic;

    SOKafkaConsumer(String topic) {
        this.topic = topic;
        Logger.getInstance().info("SOKafka consumer init " + topic);
        this.consumer = createConsumer();
        this.executorService = Executors.newSingleThreadExecutor();
        executorService.execute(this);
    }
    @Override
    public void run() {
        while (consumer != null) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
            if (records.count() == 0) { continue; }
            SystemOfferController controller = SystemOfferService.getInstance().getController();

            for (ConsumerRecord<String, String> record : records) {
                Logger.getInstance().debug("receive<<<"+ record.topic() + "|" + record.key() +"|" + record.value());
                switch (record.key()){
                    case SystemOfferConst.ACTION_CONNECT:
                        controller.dispatchConnected(record.value());
                        break;
                    case SystemOfferConst.ACTION_OFFERS:
                        try {
                            UserOffers systemOfferUser = gson.fromJson(record.value(), UserOffers.class);
                            controller.dispatchOffer(systemOfferUser);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case SystemOfferConst.ACTION_GET_OFFER:
                        try {
                            Offer offer = gson.fromJson(record.value(), Offer.class);
                            controller.dispatchOfferDetail(offer);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case SystemOfferConst.ACTION_GET_DATA_CUSTOM:
                        try {
                            UserDataCustom userDataCustom = gson.fromJson(record.value(), UserDataCustom.class);
                            controller.dispatchDataCustom(userDataCustom);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            consumer.commitAsync();
        }
    }

    private Consumer<String, String> createConsumer() {
        Properties props = new Properties();
        SOKafkaConfig instance = SOKafkaConfig.getInstance();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, instance.getServer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, instance.getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15000);
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, instance.getReconnectBackOff());
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, instance.getReconnectBackOffMax());

        if(instance.isEnableSSL()) {
            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
            props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, instance.getTruststorePath());
            props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, instance.getTruststorePassword());
            props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, instance.getKeystorePath());
            props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, instance.getKeystorePassword());
            props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, instance.getKeystorePassword());
            props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        }
        AdminClient adminClient = AdminClient.create(props);
        adminClient.createTopics(Stream.of(topic).map(s -> new NewTopic(s,1, (short) 1)).collect(Collectors.toList()));
        Consumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList(topic));
        return kafkaConsumer;
    }
}
