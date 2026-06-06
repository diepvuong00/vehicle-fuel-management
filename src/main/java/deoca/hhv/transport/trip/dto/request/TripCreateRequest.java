package deoca.hhv.transport.trip.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TripCreateRequest {

    @NotBlank
    private String vehicleId;

    @NotBlank
    private String driverId;

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;
}
