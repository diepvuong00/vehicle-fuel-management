package deoca.hhv.transport.trip.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripReopenResponse {

    private String tripId;

    private String tripCode;

    private String status;

    private String message;
}
