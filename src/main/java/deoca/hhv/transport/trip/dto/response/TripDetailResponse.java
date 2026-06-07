package deoca.hhv.transport.trip.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripDetailResponse {


    private String tripId;

    private String tripCode;

    private Integer month;

    private Integer year;

    private String vehicleId;

    private String licensePlate;

    private String driverId;

    private String driverName;

    private Double openingKm;

    private Double closingKm;

    private Double totalKm;

    private Double totalWorkingHour;

    private Double totalIdleHour;

    private Double totalFuelReceived;

    private Double standardFuelConsumption;

    private Double actualFuelConsumption;

    private Double exceedPercent;

    private String warningLevel;

    private String status;

    private List<TripLogDetailResponse> details;
}
