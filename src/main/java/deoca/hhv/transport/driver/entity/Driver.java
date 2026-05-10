package deoca.hhv.transport.driver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.trip.entity.TripLog;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = false, nullable = false)
    private String code;

    @Column(nullable = false)
    private String fullName;

    private LocalDate dateOfBirth;

    @Column(unique = true, nullable = false, length = 50)
    private String nationalId;

    @Column(unique = true, nullable = false, length = 20)
    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @OneToMany(
            mappedBy = "driver",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<DriverLicense> licenses;

    @OneToMany(
            mappedBy = "driver",
            fetch = FetchType.LAZY
    )
    private List<FuelIssue> fuelIssues;

    @JsonIgnore
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private List<TripLog> trips = new ArrayList<>();

    private boolean deleted = false;

    //audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
