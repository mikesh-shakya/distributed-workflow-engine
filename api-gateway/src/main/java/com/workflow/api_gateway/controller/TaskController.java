package com.workflow.api_gateway.controller;

import com.workflow.api_gateway.dto.TaskRequest;
import com.workflow.api_gateway.dto.TaskResponse;
import com.workflow.api_gateway.service.TaskService;
import com.workflow.common.model.Task;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequestMapping("/tasks")
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping()
    public ResponseEntity<Task> submit_task(@RequestBody TaskRequest request){
        Task task = taskService.create_task(request);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/status/{taskId}")
    public ResponseEntity<String> getTaskStatus(@PathVariable String taskId){
        String status = taskService.get_task_status(taskId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/{taskName}")
    public TaskResponse getTaskByTaskName(@PathVariable String taskName) {
        return taskService.getTaskByName(taskName);
    }
}
