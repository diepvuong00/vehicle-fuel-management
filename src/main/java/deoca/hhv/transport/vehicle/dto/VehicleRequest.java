package deoca.hhv.transport.vehicle.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VehicleRequest {
    @NotBlank(message = "Nhập biển số phương tiện")
    private String licensePlate;        //biển số
    @NotBlank(message = "Nhập tên phương tiện")
    private String vehicleType;         //loại xe
    private String brand;               //nhãn hiệu
    private String originCountry;       //nước sản xuất

    @NotBlank(message = "Nhập số khung phương tiện")
    private String engineNumber;        //số khung
    @NotBlank(message = "Nhập số máy phương tiện")
    private String chassisNumber;       //số máy

    private String color;               //màu
    @Min(value = 2000, message = "Năm sản xuất không hợp lệ")
    private Integer manufactureYear;    //năm sản xuất

    @Positive(message = "Dung tích nguyên liệu phải lớn hơn 0")
    private Double fuelCapacity;        //dung tích nguyên liệu

    private String imageKey;            // hình ảnh
    private String unitType;

}
