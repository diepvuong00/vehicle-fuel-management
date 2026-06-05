package deoca.hhv.transport.fuelnorm.dto.request;

import deoca.hhv.transport.fuelnorm.enums.PurposeUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurposeRequest {

    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Unit is required")
    private PurposeUnit unit;

    private Boolean applyFuelNorm;

    private String description;
}
