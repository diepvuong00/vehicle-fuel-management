package deoca.hhv.transport.trip.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class TripLogDetailCreateRequest {

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
