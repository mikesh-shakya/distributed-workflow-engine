package com.workflow.api_gateway.controller;

import com.workflow.api_gateway.dto.TaskRequest;
import com.workflow.api_gateway.service.TaskService;
import com.workflow.common.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/tasks")
@RestController
@RequiredArgsConstructor
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Task> submit_task(@RequestBody TaskRequest request){
        Task task = taskService.create_task(request);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/{task_id}")
    public ResponseEntity<String> getTaskStatus(@PathVariable String task_id){
        String status = taskService.get_task_status(task_id);
        return ResponseEntity.ok(status);
    }
}
