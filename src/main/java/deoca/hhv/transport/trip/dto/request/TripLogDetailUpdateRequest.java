package deoca.hhv.transport.trip.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TripLogDetailUpdateRequest {

    @NotNull
    private LocalDate workDate;

    @NotBlank
    private String driverId;

    private String workContent;

    private Double startKm;

    @NotNull
    private Double endKm;

    private Double workingHour;

    private Double idleHour;

    private Double fuelReceived;

    private String note;
}
