package deoca.hhv.transport.fuel.dto.reponse;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class FuelIssueResponse {

    private String id;

    private String issueCode;

    private String vehicleId;
    private String vehiclePlate;

    private String driverId;
    private String driverName;

    private String fuelType;

    private Double quantity;

    private String unit;

    private Double currentKm;

    private String fuelStation;

    private String requestedBy;

    private LocalDateTime fuelTime;

    private String note;

    private String status;

    private String approvedBy;

    private LocalDateTime approvedAt;

    private LocalDateTime createdAt;
}
