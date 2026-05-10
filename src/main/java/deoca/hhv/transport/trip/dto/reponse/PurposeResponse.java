package deoca.hhv.transport.trip.dto.reponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurposeResponse {

    private String id;

    private String code;

    private String name;

    private String unit;

    private Boolean applyFuelNorm;

    private String description;

    private Boolean active;
}
