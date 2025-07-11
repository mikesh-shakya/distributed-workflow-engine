package com.workflow.api_gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflow.api_gateway.dto.TaskRequest;
import com.workflow.api_gateway.kafka.TaskProducer;
import com.workflow.common.repository.TaskRepository;
import com.workflow.common.enums.TaskStatus;
import com.workflow.common.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskProducer taskProducer;
    @Autowired
    ObjectMapper objectMapper;

    public Task create_task(TaskRequest request){
        UUID id = UUID.randomUUID();
        Task task = Task.builder()
                .task_id(id)
                .task_name(request.getTask_name())
                .payload(toJson(request.getPayload()))
                .status(TaskStatus.PENDING)
                .retryCount(0)
                .maxRetries(request.getMaxRetries())
                .build();

        taskRepository.save(task);
        taskProducer.sendTaskToQueue(id);
        return task;
    }

    public String get_task_status(String task_id_string){
        UUID taskId = UUID.fromString(task_id_string);
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isEmpty()) {
            log.warn("Task with ID {} not found", taskId);
            return "No such task exists";
        }
        Task task = optionalTask.get();
        return task.getStatus().toString();
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize payload", e);
        }
    }
}
