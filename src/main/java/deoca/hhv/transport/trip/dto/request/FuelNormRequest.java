package deoca.hhv.transport.trip.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FuelNormRequest {

    @NotBlank(message = "ID Phương tiện không được để trống")
    private String vehicleId;

    @NotBlank(message = "ID mục đích không được để trống")
    private String purposeId;

    /*
     * Định mức
     */
    @NotNull(message = "Định mức không được để trống")
    private Double normValue;

    /*
     * L/km
     * L/h
     */
    @NotBlank(message = "Đơn vị không được để trống")
    private String displayUnit;

    @NotNull(message = "Ngày áp dụng không được để trống")
    private LocalDate effectiveDate;

    private String note;

    private Boolean active;
}
