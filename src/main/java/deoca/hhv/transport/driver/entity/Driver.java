package deoca.hhv.transport.driver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import deoca.hhv.transport.driver.enums.DriverStatus;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<DriverLicense> licenses = new ArrayList<>();

    @OneToMany(
            mappedBy = "driver",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<FuelIssue> fuelIssues;

    @JsonIgnore
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private List<TripLog> trips = new ArrayList<>();

    private boolean deleted = false;

    //audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
