package deoca.hhv.transport.vehicle.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VehicleRequest {
    @NotBlank(message = "License plate is required")
    private String licensePlate;        //biển số
    @NotBlank(message = "Vehicle Type is required")
    private String vehicleType;         //loại xe
    private String brand;               //nhãn hiệu
    private String originCountry;       //nước sản xuất

    @NotBlank(message = "EngineNumber is required")
    private String engineNumber;        //số khung
    @NotBlank(message = "ChassisNumber is required")
    private String chassisNumber;       //số máy

    private String color;               //màu
    @Min(value = 2000, message = "Invalid manufacture year")
    private Integer manufactureYear;    //năm sản xuất

    @Positive(message = "Fuel capacity must be > 0")
    private Double fuelCapacity;        //dung tích nguyên liệu

    private String imageUrl;            // hình ảnh

}
