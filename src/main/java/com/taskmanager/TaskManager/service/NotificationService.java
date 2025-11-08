package com.taskmanager.TaskManager.service;

import com.taskmanager.TaskManager.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @Async
    public void sendTaskCreated(Task task){
        log.info("[Notification] Task created: {}", task.getTitle());
    }

    @Async
    public void sendTaskDeleted(Long id){
        log.info("[Notification] Task deleted: {}", id);
    }
}
