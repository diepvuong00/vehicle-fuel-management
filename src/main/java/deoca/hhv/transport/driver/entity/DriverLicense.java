package deoca.hhv.transport.driver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "driver_licenses")
@Getter
@Setter
public class DriverLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String licenseNumber;                       // số GPLX

    private String licenseClass; // B2, C, D            // hạng bằng

    private LocalDate issueDate;                        //ngày cấp
    private LocalDate expiryDate;                       // ngày hết hạn
    private String issuePlace;                          // nơi cấp

    @Enumerated(EnumType.STRING)
    private LicenseStatus status;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    //audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
