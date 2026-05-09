package deoca.hhv.transport.fuel.entity;

import deoca.hhv.transport.driver.entity.Driver;
import deoca.hhv.transport.fuel.enums.FuelIssueStatus;
import deoca.hhv.transport.fuel.enums.FuelType;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fuel_issues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuelIssue {

    @Id
    private String id;

    @Column(name = "issue_code", nullable = false, unique = true)
    private String issueCode;

    /*
     * Xe được cấp phát
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    /*
     * Tài xế thực hiện đổ
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    /*
     * THÔNG TIN ĐỔ NHIÊN LIỆU
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "unit", nullable = false)
    private String unit;


    /*
     * Km tại thời điểm đổ
     */
    @Column(name = "current_km")
    private Double currentKm;

    /*
     * Cây xăng đổ
     */
    @Column(name = "fuel_station")
    private String fuelStation;

    /*
     * Người yêu cầu
     */
    @Column(name = "requested_by")
    private String requestedBy;

    /*
     * Thời gian đổ thực tế
     */
    @Column(name = "fuel_time")
    private LocalDateTime fuelTime;

    /*
     * Ghi chú
     */
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    /*
     * TRẠNG THÁI PHIẾU
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FuelIssueStatus status;

    /*
     * ADMIN DUYỆT
     */
    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    /*
     * AUDIT
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = FuelIssueStatus.PENDING;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    /*
     * Link ảnh hóa đơn
     */
    @Column(name = "invoice_image")
    private String invoiceImage;




}
