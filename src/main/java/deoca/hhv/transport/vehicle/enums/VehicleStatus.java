package deoca.hhv.transport.vehicle.enums;

public enum VehicleStatus {

    ACTIVE("Đang hoạt động"),
    MAINTENANCE("Bảo trì"),
    INACTIVE("Ngưng hoạt động"),
    SUSPENDED("Tạm ngưng");

    private final String description;

    VehicleStatus(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
