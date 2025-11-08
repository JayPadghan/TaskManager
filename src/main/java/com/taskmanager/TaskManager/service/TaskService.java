package com.taskmanager.TaskManager.service;

import com.taskmanager.TaskManager.model.Task;
import com.taskmanager.TaskManager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final NotificationService notificationService;

    @Transactional
    public Task createTask(Task task){
        Task saved = taskRepository.save(task);
        notificationService.sendTaskCreated(saved);
        return saved;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setTitle(updatedTask.getTitle());

        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id){
        taskRepository.deleteById(id);
        notificationService.sendTaskDeleted(id);
    }
}
