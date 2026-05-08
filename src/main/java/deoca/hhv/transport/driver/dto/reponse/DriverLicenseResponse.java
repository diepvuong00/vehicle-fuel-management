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

//    Trạng thái của gplx - còn hạn/
    private String status;

//   NORMAL / 30_DAYS / 15_DAYS / 3_DAYS / EXPIRED
    private String warningLevel;

//    Thông báo chi tiết (Ví dụ: "Bằng lái sắp hết hạn trong 30 ngày")
    private String warningMessage;

    // số ngày còn lại
    private Long remainingDays;
}
