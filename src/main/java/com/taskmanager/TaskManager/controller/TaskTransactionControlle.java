package com.taskmanager.TaskManager.controller;

import com.taskmanager.TaskManager.model.Task;
import com.taskmanager.TaskManager.service.TaskTransactionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tx-tasks")
public class TaskTransactionControlle {

    private final TaskTransactionalService taskTransactionalService;

    @PostMapping("/atomic")
    public Task atmoicTask(@RequestBody Task task){
        return taskTransactionalService.createTaskAtomic(task);
    }

    @PostMapping("/independent")
    public Task independentTask(@RequestBody Task task){
        return taskTransactionalService.createTaskWithIndependentAudit(task);
    }
}
