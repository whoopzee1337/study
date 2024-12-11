package com.malikov.study.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaProducerAutoConfiguration {

    private final CustomKafkaProperties customKafkaProperties;

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        log.info("Kafka Producer initialization");
        KafkaTemplate<String, String> template = new KafkaTemplate(new DefaultKafkaProducerFactory(this.createProducerProperties()));
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    public Map<String, Object> createProducerProperties() {

        Map<String, Object> producerProperties = new HashMap<>();
        producerProperties.put("bootstrap.servers", this.customKafkaProperties.getServer());
        producerProperties.put("key.serializer", StringSerializer.class);
        producerProperties.put("value.serializer", StringSerializer.class);
        producerProperties.put("compression.type", this.customKafkaProperties.getProducerProperties().getCompressionType());
        producerProperties.put("max.request.size", this.customKafkaProperties.getProducerProperties().getMaxRequestSize());

        return producerProperties;
    }

}
