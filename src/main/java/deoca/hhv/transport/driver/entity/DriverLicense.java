package deoca.hhv.transport.driver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import deoca.hhv.transport.driver.enums.LicenseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "driver_licenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @JsonIgnore
    private Driver driver;

    //audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
