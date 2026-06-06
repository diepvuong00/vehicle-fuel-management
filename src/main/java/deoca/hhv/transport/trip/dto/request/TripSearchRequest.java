package deoca.hhv.transport.trip.dto.request;

import lombok.Data;

@Data
public class TripSearchRequest {

    private String vehicleId;

    private Integer month;

    private Integer year;

    private Boolean closed;

    private String driverId;
}
