package deoca.hhv.transport.trip.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trip_log_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripLogDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_log_id")
    private TripLog tripLog;

    /*
       Mục đích hoạt động
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purpose_id")
    private Purpose purpose;

    /*
        Giá trị hoạt động
        VD:
        - km
        - giờ
     */
    @Column(name = "quantity")
    private Double quantity;

    /*
       Định mức áp dụng
    */
    @Column(name = "fuel_norm")
    private Double fuelNorm;

    /*
       Tiêu hao chuẩn
    */
    @Column(name = "standard_consumption")
    private Double standardConsumption;

    @Column(name = "note")
    private String note;
}
