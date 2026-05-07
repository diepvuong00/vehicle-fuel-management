package deoca.hhv.transport.driver.entity;

public enum LicenseStatus {

    VALID("Còn hạn"),  // còn hạn
    EXPIRED("Hết hạn"); // hết hạn

    private final String displayName;

    LicenseStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
