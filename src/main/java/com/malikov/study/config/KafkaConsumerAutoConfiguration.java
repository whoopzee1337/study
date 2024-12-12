package com.malikov.study.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerAutoConfiguration {

    private final CustomKafkaProperties customKafkaProperties;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        log.info("Kafka Consumer initialization");
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory(this.createConsumerProperties()));
        factory.setBatchListener(true);
        ContainerProperties containerProperties = factory.getContainerProperties();
        containerProperties.setMissingTopicsFatal(false);
        return factory;
    }

    public Map<String, Object> createConsumerProperties() {

        Map<String, Object> consumerProperties = new HashMap<>();
        consumerProperties.put("bootstrap.servers", this.customKafkaProperties.getServer());
        consumerProperties.put("key.deserializer", StringDeserializer.class);
        consumerProperties.put("value.deserializer", StringDeserializer.class);
        consumerProperties.put("group.id", this.customKafkaProperties.getConsumerProperties().getGroupId());
        consumerProperties.put("enable.auto.commit", true);
        consumerProperties.put("fetch.max.bytes", this.customKafkaProperties.getConsumerProperties().getFetchMaxBytes());
        consumerProperties.put("max.partition.fetch.bytes", this.customKafkaProperties.getConsumerProperties().getMaxPartitionFetchBytes());
        consumerProperties.put("max.poll.interval.ms", this.customKafkaProperties.getConsumerProperties().getMaxPollIntervalMs());
        consumerProperties.put("max.poll.records", this.customKafkaProperties.getConsumerProperties().getMaxPollRecords());

        return consumerProperties;
    }
}
