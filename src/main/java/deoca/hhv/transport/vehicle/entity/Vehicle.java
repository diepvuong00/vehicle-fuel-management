package deoca.hhv.transport.vehicle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String licensePlate;        //biển số
    private String vehicleType;         //loại xe
    private String brand;               //nhãn hiệu
    private String originCountry;       //nước sản xuất

    private String engineNumber;        //số máy
    private String chassisNumber;       //số khung

    private String color;               //màu
    private Integer manufactureYear;    //năm sản xuất

    private Double fuelCapacity;        //dung tích nguyên liệu

    private String imageUrl;            // hình ảnh

    @Enumerated(EnumType.STRING)
    private VehicleStatus status = VehicleStatus.ACTIVE;

    private boolean deleted = false;

    //audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;




}
