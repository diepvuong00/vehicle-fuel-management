package deoca.hhv.transport.driver.dto.request;

import deoca.hhv.transport.driver.entity.LicenseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DriverLicenseRequest {

    @NotBlank(message = "Số giấy phép lái xe không được để trống")
    private String licenseNumber;

    @NotBlank(message = "Hạng bằng không được để trống")
    private String licenseClass;

    @NotNull(message = "Ngày cấp không được để trống")
    private LocalDate issueDate;

    @NotNull(message = "Ngày hết hạn không được để trống")
    private LocalDate expiryDate;

    @NotBlank(message = "Nơi cấp không được để trống")
    private String issuePlace;

//    @NotNull(message = "Trạng thái bằng lái là bắt buộc")
//    private LicenseStatus status;
//
//    @NotBlank(message = "ID tài xế là bắt buộc để liên kết bằng lái")
//    private String driverId;
}
