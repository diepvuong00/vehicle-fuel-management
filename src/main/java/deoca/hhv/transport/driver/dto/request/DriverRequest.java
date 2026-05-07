package deoca.hhv.transport.driver.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import deoca.hhv.transport.driver.entity.DriverStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DriverRequest {

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotNull(message = "Ngày tháng năm sinh không được để trống")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Số căn cước là bắt buộc")
    @Pattern(regexp = "^[0-9]{12}$", message = "Số căn cước có 12 số")
    private String nationalId;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải có 10 chữ số")
    private String phone;

    private String address;

    @NotNull(message = "Trạng thái tài xế là bắt buộc")
    private DriverStatus status;

    private List<DriverLicenseRequest> licenses;
}
