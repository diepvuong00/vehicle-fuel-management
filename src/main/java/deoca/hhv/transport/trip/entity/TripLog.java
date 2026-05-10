package deoca.hhv.transport.trip.entity;

import deoca.hhv.transport.driver.entity.Driver;
import deoca.hhv.transport.trip.enums.FuelWarningLevel;
import deoca.hhv.transport.trip.enums.TripStatus;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Column(name = "trip_code", unique = true, nullable = false)
    private String tripCode;

    /*
       Xe thực hiện nhật trình
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    /*
       Tài xế thực hiện
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    /*
        Ngày hoạt động
     */
    @Column(name = "trip_date", nullable = false)
    private LocalDate tripDate;

    /*
        Km đầu
     */
    @Column(name = "start_odometer")
    private Double startOdometer;

    /*
        Km cuối
     */
    @Column(name = "end_odometer")
    private Double endOdometer;

    /*
       Tổng km
    */
    @Column(name = "total_distance")
    private Double totalDistance;

    /*
       Tổng giờ hoạt động
    */
    @Column(name = "total_operating_hours")
    private Double totalOperatingHours;

    /*
       Nhiên liệu thực tế đã cấp
    */
    @Column(name = "actual_fuel")
    private Double actualFuel;


    /*
        Nhiên liệu tiêu chuẩn
     */
    @Column(name = "standard_fuel")
    private Double standardFuel;

    /*
        Sai lệch
     */
    @Column(name = "fuel_difference")
    private Double fuelDifference;

    /*
        % lệch
     */
    @Column(name = "difference_percent")
    private Double differencePercent;

    /*
       Kết quả cảnh báo
    */
    @Enumerated(EnumType.STRING)
    @Column(name = "warning_level")
    private FuelWarningLevel warningLevel;

    /*
        Ghi chú
     */
    @Column(name = "note")
    private String note;

    /*
        Trạng thái
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TripStatus status;

    /*
        Danh sách hoạt động
     */
    @OneToMany(
            mappedBy = "tripLog",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TripLogDetail> details = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
