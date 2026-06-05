package deoca.hhv.transport.trip.entity;

import deoca.hhv.transport.driver.entity.Driver;
import deoca.hhv.transport.fuelnorm.enums.FuelWarningLevel;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip_log")
@Builder
public class TripLog {

    @Id
    private String id;

    private Integer month;

    private Integer year;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private Driver driver;

//    Số km xe lúc bắt đầu tháng/chuyến đi
    private Double openingKm;

//    Số km xe lúc kết thúc tháng/chuyến đi
    private Double closingKm;

//    Tổng số km xe đã di chuyển trong kỳ
    private Double totalKm;

//    Tổng số giờ làm việc
    private Double totalWorkingHour;

//    Tổng số giờ nổ máy chờ
    private Double totalIdleHour;

//    Tổng số lượng nhiên liệu mà xe đã nhận
    private Double totalFuelReceived;

//    Lượng nhiên liệu tiêu hao thực tế của xe.
    private Double standardFuelConsumption;

//    Lượng nhiên liệu tiêu hao thực tế của xe.
    private Double actualFuelConsumption;

//    Phần trăm vượt định mức nhiên liệu
    private Double exceedPercent;

    @Enumerated(EnumType.STRING)
    private FuelWarningLevel warningLevel;

//    Trạng thái Đóng/Chốt sổ của nhật ký
    private Boolean closed;

}
