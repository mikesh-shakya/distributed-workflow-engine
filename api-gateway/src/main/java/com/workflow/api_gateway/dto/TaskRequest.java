package com.workflow.api_gateway.dto;

import lombok.Data;

import java.util.Map;

@Data
public class TaskRequest {
    private String taskName;
    private Map<String, Object> payload;
    private int maxRetries = 3;
}
