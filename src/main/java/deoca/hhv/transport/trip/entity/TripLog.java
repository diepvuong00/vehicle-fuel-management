package deoca.hhv.transport.trip.entity;

import deoca.hhv.transport.driver.entity.Driver;
import deoca.hhv.transport.fuelnorm.enums.FuelWarningLevel;
import deoca.hhv.transport.trip.enums.TripStatus;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip_log")
@Builder
public class TripLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String tripCode;

    private Integer month;

    private Integer year;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private Driver driver;

    private Double openingKm;

    private Double closingKm;

    private Double totalKm;

    private Double totalWorkingHour;

    private Double totalIdleHour;

    private Double totalFuelReceived;

    private Double standardFuelConsumption;

    private Double actualFuelConsumption;

    private Double exceedPercent;

    @Enumerated(EnumType.STRING)
    private FuelWarningLevel warningLevel;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    private Boolean closed;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "tripLog",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TripLogDetail> tripLogDetails;

}
