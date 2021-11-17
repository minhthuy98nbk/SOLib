package zingplay;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import zingplay.log.Logger;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SOKafkaProducer {
    private static SOKafkaProducer instance;
    private static volatile Boolean loaded = false;
    private Producer<String, String> producer;
    private ExecutorService executorService;
    private ArrayBlockingQueue<SOKafkaItemMsg> logQueue;
    SOKafkaConsumer soKafkaConsumer;
    void startUp() {
        if(SOKafkaConfig.getInstance().isEnable()) {
            synchronized (loaded) {
                if(!loaded) {
                    loaded = true;
                    producer = createProducer();
                    Logger.getInstance().info("SOKafka producer init");
                    soKafkaConsumer = new SOKafkaConsumer(SOKafkaConfig.getInstance().getTopicResponse());
                }
            }
        }
    }

    private SOKafkaProducer() {
        logQueue = new ArrayBlockingQueue<SOKafkaItemMsg>(SOKafkaConfig.getInstance().getQueueCapacity());
        this.executorService = Executors.newFixedThreadPool(SOKafkaConfig.getInstance().getThreadPoolSize());
        for(int i = 0; i < SOKafkaConfig.getInstance().getThreadPoolSize(); i++) {
            this.executorService.execute(new WriteWorker(i));
        }
    }

    static SOKafkaProducer getInstance() {
        if (instance == null) {
            synchronized (loaded) {
                if (instance == null) {
                    instance = new SOKafkaProducer();
                }
            }
        }
        return instance;
    }

    void send(String action, String msg){
        if(!SOKafkaConfig.getInstance().isEnable()) {
            return;
        }
        try{
            if(action == null || msg == null) {
                Logger.getInstance().info("SOKafka plz pass parameter!");
                return;
            }
            SOKafkaItemMsg soKafkaItemMsg = new SOKafkaItemMsg(action, msg);
            if(!logQueue.offer(soKafkaItemMsg)){
                logQueue.poll();
                logQueue.offer(soKafkaItemMsg);
            }
        }catch (Exception e) {
            Logger.getInstance().info("producer send error " + e.toString());
        }
    }

    private void write(SOKafkaItemMsg itemMsg) {
        SOKafkaConfig instance = SOKafkaConfig.getInstance();
        if(!instance.isEnable()) {
            return;
        }
        String key = String.format("%s|%s|%s", instance.game, instance.country, itemMsg.getAction());
        Logger.getInstance().info("System offer write kafka ", itemMsg.getMsg(), key);
        //ProducerRecord<String, String> record = new ProducerRecord<String, String>(SOKafkaConfig.getInstance().getTopic(), SOKafkaConfig.getInstance().getServerIndex(), System.currentTimeMillis(), key, itemMsg.getMsg());
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(instance.getTopic(), null, key, itemMsg.getMsg());
        producer.send(record);
    }


    private Producer<String, String> createProducer() {
        Properties props = new Properties();
        SOKafkaConfig instance = SOKafkaConfig.getInstance();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, instance.getServer());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, instance.getClientId());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15000);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, instance.getReconnectBackOff());
        props.put(ProducerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, instance.getReconnectBackOffMax());
        if(instance.isEnableSSL()) {
            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
            props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, instance.getTruststorePath());
            props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, instance.getTruststorePassword());
            props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, instance.getKeystorePath());
            props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, instance.getKeystorePassword());
            props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, instance.getKeystorePassword());
            props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        }
        return new KafkaProducer<String, String>(props);
    }

    public static Boolean getLoaded() {
        return loaded;
    }

    public void sendTest(String message) {
        String topic = "system_offer_game_country_test";
        String key = SOKafkaConfig.getInstance().getTopicResponse();
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, key, message);
        Logger.getInstance().debug("send >>>"+ record.topic() + "|" + record.key() +"|" + record.value()) ;
        producer.send(record);
    }


    class WriteWorker implements Runnable {
        public WriteWorker(int index) {
            Thread.currentThread().setName("system-offers-kafka-worker-" + index);
        }
        @Override
        public void run() {
            while (getLoaded()){
                try {
                    SOKafkaItemMsg msg = logQueue.take();
                    if(msg != null) {
                        write(msg);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
