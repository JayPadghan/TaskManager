package com.taskmanager.TaskManager.service;

import com.taskmanager.TaskManager.model.AuditLog;
import com.taskmanager.TaskManager.model.Task;
import com.taskmanager.TaskManager.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogIndependentService {
    private final AuditLogRepository auditLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAuditLogIndependent(Task task){

        AuditLog log = new AuditLog();
        log.setAction("CREATE");
        log.setEntity("Task");
        log.setEntityId(task.getId());
        log.setDetails("Title='" + task.getTitle() + "'");
        log.setTimeStamp(LocalDateTime.now());

        auditLogRepository.save(log);

        // Simulate failure
        if (task.getTitle().contains("fail")) {
            throw new RuntimeException("Audit failed, but Task stays!");
        }
    }
}
