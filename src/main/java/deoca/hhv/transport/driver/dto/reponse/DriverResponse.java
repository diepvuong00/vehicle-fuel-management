package deoca.hhv.transport.driver.dto.reponse;

import deoca.hhv.transport.driver.entity.DriverStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DriverResponse {

    private String id;
    private String code;
    private String fullName;
    private LocalDate dateOfBirth;
    private String nationalId;
    private String phone;
    private String address;
    private String status;

    private List<DriverLicenseResponse> licenses;
}
