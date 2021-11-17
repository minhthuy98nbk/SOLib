package zingplay;

import zingplay.log.Logger;

class SOKafkaConfig {
    public boolean isEnable;
    public String server;
    public String id;
    public String game;
    public String country;
    public String topic;
    public String topicResponse;
    int threadPoolSize;
    int queueCapacity = 100000;
    int reconnectBackOff = 100000;
    int reconnectBackOffMax = 100000;
    public int serverIndex = 0;

    String truststorePath, truststorePassword, keystorePath, keystorePassword;
    boolean enableSSL = false;

    private static SOKafkaConfig instance;
    static SOKafkaConfig getInstance() {
        if(instance == null) {
            instance = new SOKafkaConfig();
        }
        return instance;
    }

    public void loadConfig(String path){
        SOKafkaConfig config = new SOKafkaConfig();

        SystemOfferProperties instance = SystemOfferProperties.getInstance(path);
        config.isEnable = instance.getBoolean("system.offer.enable");
        config.server = instance.get("system.offer.server");
        config.id = instance.get("system.offer.index");
        config.game = instance.get("system.offer.game");
        config.country = instance.get("system.offer.country");
        config.topic = instance.get("system.offer.topic_tool");
        config.topicResponse = instance.get("system.offer.topic_game");
        config.threadPoolSize = instance.getInt("system.offer.threadPoolSize");
        config.queueCapacity = instance.getInt("system.offer.queueCapacity");
        config.truststorePath = instance.get("system.offer.truststorePath");
        config.truststorePassword = instance.get("system.offer.truststorePassword");
        config.keystorePath = instance.get("system.offer.keystorePath");
        config.keystorePassword = instance.get("system.offer.keystorePassword");
        config.enableSSL = instance.getBoolean("system.offer.enableSSL");
        config.reconnectBackOff = instance.getInt("system.offer.reconnect.backoff.ms");
        config.reconnectBackOffMax = instance.getInt("system.offer.reconnect.backoff.max.ms");

        SOKafkaConfig.instance = config;
        Logger.getInstance().info("load config kafka " + config.server + "| " + config.topic + "|" + config.topicResponse);
    }

    public boolean isEnable() {
        return this.isEnable;
    }

    public String getServer() {
        return server;
    }

    public String getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public String getTruststorePath() {
        return truststorePath;
    }

    public String getTruststorePassword() {
        return truststorePassword;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public boolean isEnableSSL() {
        return enableSSL;
    }

    public int getServerIndex() {
        return serverIndex;
    }

    public String getTopicResponse() {
        return topicResponse;
    }

    public String getGroupId() {
        return game +"_"+ country +"_"+ id;
    }

    public String getClientId() {
        return game +"_"+ country +"_"+ id;
    }

    public int getReconnectBackOff() {
        return reconnectBackOff;
    }

    public int getReconnectBackOffMax() {
        return reconnectBackOffMax;
    }
}
