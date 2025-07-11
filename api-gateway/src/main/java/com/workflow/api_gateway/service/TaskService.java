package com.workflow.api_gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflow.api_gateway.dto.TaskRequest;
import com.workflow.api_gateway.dto.TaskResponse;
import com.workflow.api_gateway.exceptionHandling.ResourceNotFoundException;
import com.workflow.api_gateway.kafka.TaskProducer;
import com.workflow.common.repository.TaskRepository;
import com.workflow.common.enums.TaskStatus;
import com.workflow.common.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
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
                .taskId(id)
                .taskName(request.getTaskName())
                .payload(toJson(request.getPayload()))
                .status(TaskStatus.PENDING)
                .retryCount(0)
                .maxRetries(request.getMaxRetries())
                .createdAt(LocalDateTime.now())
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

    public TaskResponse getTaskByName(String taskName){
        Task task = taskRepository.findByTaskName(taskName)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + taskName));

        TaskResponse taskResponse = TaskResponse.builder()
                .taskName(task.getTaskName())
                .payload(task.getPayload())
                .status(task.getStatus())
                .retryCount(task.getRetryCount())
                .maxRetries(task.getMaxRetries())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .error(task.getError())
                .build();
        return taskResponse;
    }
}
