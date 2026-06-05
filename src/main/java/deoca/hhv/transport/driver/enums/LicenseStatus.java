package deoca.hhv.transport.driver.enums;

public enum LicenseStatus {

    VALID("Còn hiệu lực"),  // còn hạn
    EXPIRED("Hết hiệu lực"); // hết hạn

    private final String displayName;

    LicenseStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
