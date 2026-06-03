package deoca.hhv.transport.report.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleExportDto  {

    private String licensePlate;

    private String vehicleType;

    private String fuelType;

    private Double distanceNorm;

    private Double workingHourNorm;

    private Double idleHourNorm;

    private String status;
}
