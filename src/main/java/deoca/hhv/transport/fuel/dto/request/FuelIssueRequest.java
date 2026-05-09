package deoca.hhv.transport.fuel.dto.request;

import deoca.hhv.transport.fuel.enums.FuelType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FuelIssueRequest {

    @NotBlank(message = "Vehicle id is required")
    private String vehicleId;

    @NotBlank(message = "Driver id is required")
    private String driverId;

    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    private Double quantity;

    @NotBlank(message = "Unit is required")
    private String unit;

    private Double currentKm;

    private String fuelStation;

    private String requestedBy;

    private LocalDateTime fuelTime;

    private String note;

    private String invoiceImage;
}
