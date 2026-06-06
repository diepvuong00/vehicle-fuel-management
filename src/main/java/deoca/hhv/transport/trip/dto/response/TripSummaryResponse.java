package deoca.hhv.transport.trip.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripSummaryResponse {

    private String id;

    private String tripCode;

    private String vehicleId;

    private String licensePlate;

    private String driverId;

    private String driverName;

    private Integer month;

    private Integer year;

    private Double totalKm;

    private Double totalFuelReceived;

    private Double actualFuelConsumption;

    private Double standardFuelConsumption;

    private Double exceedPercent;

    private String warningLevel;

    private String status;
}
