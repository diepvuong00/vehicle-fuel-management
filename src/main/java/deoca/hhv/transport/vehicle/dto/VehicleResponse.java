package deoca.hhv.transport.vehicle.dto;

import lombok.Data;

@Data
public class VehicleResponse {

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

    private String imageUrl;
}
