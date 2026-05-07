package deoca.hhv.transport.driver.dto.reponse;

import deoca.hhv.transport.driver.entity.LicenseStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DriverLicenseResponse {

    private String id;
    private String licenseNumber;
    private String licenseClass;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String issuePlace;

    private String status;

//    Cảnh báo hết hạn, tính toán ở Service
//    private boolean isExpiringSoon;

//    Thông báo chi tiết (Ví dụ: "Bằng lái sắp hết hạn trong 5 ngày")
//    private String expiryWarningMessage;
}
