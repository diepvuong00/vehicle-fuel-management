package deoca.hhv.transport.driver.entity;

public enum DriverStatus {

    ACTIVE("Đang làm"),     // hoạt động
    INACTIVE("Đã nghỉ"),   // ngưng hoạt động
    SUSPENDED("Tạm nghỉ");   // tạm nghỉ

    private final String displayName;

    DriverStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
