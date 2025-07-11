package com.workflow.api_gateway.dto;

import com.workflow.common.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private String taskName;
    private String payload; // JSON as string...
    private TaskStatus status;
    private int retryCount;
    private int maxRetries;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String error;
}
