package com.taskmanager.TaskManager.service;

import com.taskmanager.TaskManager.model.AuditLog;
import com.taskmanager.TaskManager.model.Task;
import com.taskmanager.TaskManager.repository.AuditLogRepository;
import com.taskmanager.TaskManager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskTransactionalService {
    private final TaskRepository taskRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuditLogIndependentService auditLogIndependentService;

    //    case 1 : Atomic transaction
    @Transactional
    public Task createTaskAtomic(Task task){
        Task savedTask = taskRepository.save(task);

        // simulate failure
        if (task.getTitle().contains("fail")){
            throw new RuntimeException("Simulated Audit Failure!");
        }

        AuditLog auditLog = new AuditLog();
        auditLog.setAction("CREATE");
        auditLog.setEntity("Task");
        auditLog.setEntityId(savedTask.getId());
        auditLog.setDetails("Title='" + savedTask.getTitle()+"'");
        auditLog.setTimeStamp(LocalDateTime.now());
        auditLogRepository.save(auditLog);

        return savedTask;
    }

    //    case 2 : Independent Transaction (Task saved even if audit fails)
    @Transactional
    public Task createTaskWithIndependentAudit(Task task){
        Task savedTask = taskRepository.save(task);
        try {
            auditLogIndependentService.saveAuditLogIndependent(savedTask);
        } catch (Exception e) {
            // Log and swallow so Task transaction is not rolled back
            System.err.println("Audit failed independently: " + e.getMessage());
        }
        return savedTask;
    }


}
