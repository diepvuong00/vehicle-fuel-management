package deoca.hhv.transport.fuel.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuelIssueCancelRequest {

    @NotBlank(
            message = "Lý do hủy không được để trống"
    )
    private String cancelReason;
}
