package deoca.hhv.transport.trip.dto.response;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripLogDetailDeleteResponse {

    private String detailId;

    private String tripId;

    private String message;

    private LocalDateTime deletedAt;
}
