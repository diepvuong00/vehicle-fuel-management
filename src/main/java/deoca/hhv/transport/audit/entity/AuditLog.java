package deoca.hhv.transport.audit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "audit_log")
@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String action;        // CREATE / UPDATE / DELETE
    private String entityName;    // VEHICLE
    private String entityId;

    @Column(columnDefinition = "TEXT")
    private String oldData;
    @Column(columnDefinition = "TEXT")
    private String newData;

    private String createdBy;
    private LocalDateTime createdAt;
}
