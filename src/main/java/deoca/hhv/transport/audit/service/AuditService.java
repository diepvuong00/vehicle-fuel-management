package deoca.hhv.transport.audit.service;

import deoca.hhv.transport.audit.entity.AuditLog;
import deoca.hhv.transport.audit.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditLogRepository repository;
    private final ObjectMapper objectMapper;

    public AuditService(AuditLogRepository repository) {
        this.repository = repository;
        this.objectMapper = new ObjectMapper();
    }

    public void log(String action, String entityName, String entityId,
                    Object oldData, Object newData) {

        try {
            AuditLog log = new AuditLog();

            log.setAction(action);
            log.setEntityName(entityName);
            log.setEntityId(entityId);
            log.setOldData(oldData != null ? objectMapper.writeValueAsString(oldData) : null);
            log.setNewData(newData != null ? objectMapper.writeValueAsString(newData) : null);

            log.setCreatedBy("SYSTEM"); // sau này thay bằng user login
            log.setCreatedAt(LocalDateTime.now());

            repository.save(log);

        } catch (Exception e) {
            // không được crash hệ thống
            e.printStackTrace();
        }
    }
}
