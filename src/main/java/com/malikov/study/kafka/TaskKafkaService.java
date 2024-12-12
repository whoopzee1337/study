package com.malikov.study.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malikov.study.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskKafkaService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${study.kafka.topics.task-status}")
    private String topicName;

    @SneakyThrows(JsonProcessingException.class)
    @KafkaListener(topics = {"${study.kafka.topics.task-status}"},
            containerFactory = "kafkaListenerContainerFactory",
            batch = "true")
    public void consume(ConsumerRecords<?, String> records) {
        for (ConsumerRecord<?, String> message : records) {
            TaskDto response = objectMapper.readValue(message.value(), TaskDto.class);
            log.info("Received message {} from kafka topic {}", objectMapper.writeValueAsString(response), topicName);
        }
    }

    @SneakyThrows(JsonProcessingException.class)
    public void sendMessage(TaskDto message) {
        kafkaTemplate.send(topicName, objectMapper.writeValueAsString(message));
    }
}
