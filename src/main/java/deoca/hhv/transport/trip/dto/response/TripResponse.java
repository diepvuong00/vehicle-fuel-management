package deoca.hhv.transport.trip.dto.response;

import deoca.hhv.transport.fuelnorm.enums.FuelWarningLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
@Data
public class TripResponse {

    private String tripCode;

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

//    private Boolean closed;

    private List<TripLogDetailResponse> details;

    private String status;
}
