package com.workflow.api_gateway.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "task-queue";

    public void sendTaskToQueue(UUID taskId) {
        kafkaTemplate.send(TOPIC, taskId.toString());
    }
}
