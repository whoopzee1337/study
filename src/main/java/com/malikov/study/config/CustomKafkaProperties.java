package com.malikov.study.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "study.kafka")
public class CustomKafkaProperties {

    private String server;
    @NestedConfigurationProperty
    private ConsumerProperties consumerProperties;
    @NestedConfigurationProperty
    private ProducerProperties producerProperties;

    @Getter
    public static class ConsumerProperties {
        private boolean enabled = true;
        private String groupId;
        private int fetchMaxBytes = 10485760;
        private int maxPartitionFetchBytes = 10485760;
        private int maxPollIntervalMs = 300000;
        private int maxPollRecords = 500;
    }

    @Getter
    public static class ProducerProperties {
        private boolean enabled = true;
        private String compressionType = "gzip";
        private int maxRequestSize = 10485760;
    }
}