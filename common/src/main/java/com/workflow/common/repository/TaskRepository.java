package com.workflow.common.repository;

import com.workflow.common.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    public Optional<Task> findByTaskName(String task_name);
}
