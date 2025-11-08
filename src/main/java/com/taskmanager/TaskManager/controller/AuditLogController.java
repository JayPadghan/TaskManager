package com.taskmanager.TaskManager.controller;

import com.taskmanager.TaskManager.model.AuditLog;
import com.taskmanager.TaskManager.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audit-logs")
public class AuditLogController {
    private final AuditLogRepository auditLogRepository;

    @GetMapping("getAllLogs")
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    @GetMapping("{id}")
    public AuditLog getLogById(@PathVariable Long id){
        return auditLogRepository.findById(id).orElseThrow(() -> new RuntimeException("Log Not Found!"));
    }
}
