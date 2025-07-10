package com.workflow.executor.consumer;

import com.workflow.executor.service.TaskExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskConsumer {

    private final TaskExecutorService executorService;

    @KafkaListener(topics = "task-queue", groupId = "task-consumer-group")
    public void listen(String taskId) {
        executorService.processTask(taskId);
    }
}
