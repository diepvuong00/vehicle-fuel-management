package deoca.hhv.transport.trip.entity;

import deoca.hhv.transport.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "fuel_norms",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_vehicle_purpose",
                        columnNames = {
                                "vehicle_id",
                                "purpose_id"
                        }
                )
        }
)
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
       Gía trị định mức
       VD:
       0.3L/km
       3L/h
    */
    @Column(name = "norm_value")
    private Double normValue;

    /*
     * Đơn vị hiển thị
     * VD:
     * L/100km
     * L/h hoạt động
     * L/h nổ máy
     */
    @Column(name = "display_unit")
    private String displayUnit;

    /*
        Có hiệu lực từ ngày
     */
    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    /*
        Hết hiệu lực
     */
//    @Column(name = "effective_to")
//    private LocalDate effectiveTo;

    /*
        Đang áp dụng
     */
    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "note")
    private String note;
}
