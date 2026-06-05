package deoca.hhv.transport.trip.dto.response;

import deoca.hhv.transport.fuelnorm.enums.FuelWarningLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class TripResponse {

    private String id;

    private String vehicleId;

    private String licensePlate;

    private String driverId;

    private String driverName;

    private Integer month;

    private Integer year;

    private Double totalKm;

    private Double totalWorkingHour;

    private Double totalIdleHour;

    private Double totalFuelReceived;

    private FuelWarningLevel warningLevel;

    private Boolean closed;

    private List<TripLogDetailResponse> details;
}
