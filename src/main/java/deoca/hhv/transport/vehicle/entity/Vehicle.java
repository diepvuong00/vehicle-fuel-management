package deoca.hhv.transport.vehicle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.fuelnorm.entity.FuelNorm;
import deoca.hhv.transport.trip.entity.TripLog;
import deoca.hhv.transport.vehicle.enums.UnitType;
import deoca.hhv.transport.vehicle.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "image_url")
    private String imageKey;            // hình ảnh

    @Enumerated(EnumType.STRING)
    private VehicleStatus status = VehicleStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private UnitType unitType;          // đơn vị tính km/Hour

    private boolean deleted = false;

    @OneToMany(
            mappedBy = "vehicle",
            fetch = FetchType.LAZY
    )
    private List<FuelIssue> fuelIssues;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    private List<TripLog> trips = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle")
    private List<FuelNorm> fuelNorms = new ArrayList<>();

    //audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
