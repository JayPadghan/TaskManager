package com.taskmanager.TaskManager.controller;

import com.taskmanager.TaskManager.model.Task;
import com.taskmanager.TaskManager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-manager/")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskService.createTask(task);
    }

    @GetMapping("getAllTasks")
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @PutMapping("{id}")
    public Task updateTask(@RequestBody Task task, @PathVariable Long id){
        return taskService.updateTask(id,task);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }
}
