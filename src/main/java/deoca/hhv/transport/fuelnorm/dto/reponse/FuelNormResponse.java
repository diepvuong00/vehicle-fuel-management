package deoca.hhv.transport.fuelnorm.dto.reponse;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FuelNormResponse {

    private String id;

    private String vehicleId;

    private String vehicleName;

    private String vehiclePlate;

    private String purposeId;

    private String purposeName;

    private Double normValue;

    private String displayUnit;

    private LocalDate effectiveDate;

    private String note;

    private Boolean active;
}
