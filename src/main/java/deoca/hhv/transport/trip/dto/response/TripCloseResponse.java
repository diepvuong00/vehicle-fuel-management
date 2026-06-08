package deoca.hhv.transport.trip.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripCloseResponse {

    private String tripId;

    private String tripCode;

    private Double totalKm;

    private Double totalWorkingHour;

    private Double totalIdleHour;

    private Double totalFuelReceived;

    private Double standardFuelConsumption;

    private Double actualFuelConsumption;

    private Double exceedPercent;

    private String warningLevel;

    private String status;

    
}
