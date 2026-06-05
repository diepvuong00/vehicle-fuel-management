package deoca.hhv.transport.trip.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TripLogDetailCreateRequest {

    @NotNull
    private LocalDate workDate;

    private String workContent;

    private Double startKm;

    private Double endKm;

    private Double workingHour;

    private Double idleHour;

    private String note;
}
