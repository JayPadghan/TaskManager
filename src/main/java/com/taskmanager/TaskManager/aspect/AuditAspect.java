package com.taskmanager.TaskManager.aspect;

import com.taskmanager.TaskManager.model.AuditLog;
import com.taskmanager.TaskManager.model.Task;
import com.taskmanager.TaskManager.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditLogRepository auditLogRepository;

    @Pointcut("execution(* com.taskmanager.TaskManager.service.TaskService.createTask(..))")
    public void createTask(){}

    @Pointcut("execution(* com.taskmanager.TaskManager.service.TaskService.updateTask(..))")
    public void updateTask(){}

    @Pointcut("execution(* com.taskmanager.TaskManager.service.TaskService.deleteTask(..))")
    public void deleteTask(){}

    @AfterReturning(pointcut = "createTask()", returning = "result")
    public void logCreateTask(JoinPoint jp, Object result) {
        if (result instanceof Task task) {
            saveAudit("CREATE", "Task", task.getId(),
                    "Title='" + task.getTitle() + "', Status=" + task.getStatus(),
                    LocalDateTime.now());
        }
    }

    @AfterReturning(pointcut = "updateTask()", returning = "result")
    public void logUpdateTask(JoinPoint jp, Object result) {
        if (result instanceof Task task) {
            saveAudit("UPDATE", "Task", task.getId(),
                    "Title='" + task.getTitle() + "', Status=" + task.getStatus(),
                    LocalDateTime.now());
        }
    }

    @AfterReturning(pointcut = "deleteTask()")
    public void logDeleteTask(JoinPoint jp) {
        Object id = jp.getArgs()[0]; // first arg = taskId
        saveAudit("DELETE", "Task", (Long)id, "DELETED",
                LocalDateTime.now());
    }

    // Helper
    public void saveAudit(
            String action,
            String entity,
            Long entityId,
            String details,
            LocalDateTime timeStamp){
        AuditLog auditLog = AuditLog.builder()
                .action(action)
                .entity(entity)
                .entityId(entityId)
                .details(details)
                .timeStamp(timeStamp)
                .build();
        auditLogRepository.save(auditLog);
    }

}
