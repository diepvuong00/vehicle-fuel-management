package deoca.hhv.transport.trip.entity;

import deoca.hhv.transport.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "fuel_norms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuelNorm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /*
       Xe áp dụng
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    /*
       Mục đích
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purpose_id")
    private Purpose purpose;

    /*
       Định mức
       VD:
       0.3L/km
       3L/h
    */
    @Column(name = "norm_value")
    private Double normValue;

    /*
        Có hiệu lực từ ngày
     */
    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    /*
        Hết hiệu lực
     */
    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    /*
        Đang áp dụng
     */
    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "note")
    private String note;
}
