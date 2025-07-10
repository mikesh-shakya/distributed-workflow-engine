package com.workflow.executor.service;

import com.workflow.common.repository.TaskRepository;
import com.workflow.common.enums.TaskStatus;
import com.workflow.common.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskExecutorService {
    @Autowired
    private TaskRepository taskRepository;

    public void processTask(String task_id_string) {
        UUID taskId = UUID.fromString(task_id_string);
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isEmpty()) {
            log.warn("Task with ID {} not found", taskId);
            return;
        }

        Task task = optionalTask.get();
        if (task.getStatus() == TaskStatus.SUCCESS || task.getStatus() == TaskStatus.FAILED) {
            log.info("Skipping task {}, already {}", taskId, task.getStatus());
            return;
        }

        try {
            log.info("Processing task {}...", taskId);
            task.setStatus(TaskStatus.RUNNING);
            taskRepository.save(task);

            // Simulate task execution
            simulateWork(task);

            task.setStatus(TaskStatus.SUCCESS);
            taskRepository.save(task);
            log.info("Task {} completed successfully", taskId);

        } catch (Exception e) {
            log.error("Task {} failed: {}", taskId, e.getMessage());
            task.setRetryCount(task.getRetryCount() + 1);
            task.setError(e.getMessage());

            if (task.getRetryCount() > task.getMaxRetries()) {
                task.setStatus(TaskStatus.FAILED);
                log.warn("Task {} exceeded max retries", taskId);
            } else {
                task.setStatus(TaskStatus.PENDING); // will be retried
                log.info("Task {} will be retried (attempt {})", taskId, task.getRetryCount());
            }

            taskRepository.save(task);
        }
    }

    private void simulateWork(Task task) throws Exception {
        // Simulate real task logic here
        Thread.sleep(1000);

        // Fail condition for testing
        if ("fail".equalsIgnoreCase(task.getTask_name())) {
            throw new RuntimeException("Intentional failure for task testing");
        }
    }
}
