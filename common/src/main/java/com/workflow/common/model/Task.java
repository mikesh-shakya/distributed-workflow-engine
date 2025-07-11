package com.workflow.common.model;

import com.workflow.common.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @Column(nullable = false)
    private UUID taskId;
    @Column(nullable = false)
    private String taskName;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String payload; // JSON as string...

    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private int retryCount;
    private int maxRetries;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Lob
    private String error;
}
