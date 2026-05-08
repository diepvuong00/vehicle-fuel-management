package deoca.hhv.transport.driver.util;

import deoca.hhv.transport.driver.dto.reponse.DriverLicenseResponse;
import deoca.hhv.transport.driver.entity.DriverLicense;
import deoca.hhv.transport.driver.enums.LicenseWarningLevel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LicenseWarningUtil {

    private LicenseWarningUtil() {
    }

    public static void setLicenseWarning(
            DriverLicense license,
            DriverLicenseResponse response
    ) {
        LocalDate today = LocalDate.now();

        long remainingDays =
                ChronoUnit.DAYS.between(
                        today,
                        license.getExpiryDate()
                );

        response.setRemainingDays(remainingDays);

        // HẾT HẠN
        if (remainingDays < 0) {

            response.setStatus("Hết hạn GPLX");

            response.setWarningLevel(
                    LicenseWarningLevel.EXPIRED.name()
            );

            response.setWarningMessage(
                    "GPLX đã hết hạn"
            );

            return;
        }

        // CÒN HẠN
        response.setStatus("Còn hạn");

        // 3 ngày
        if (remainingDays <= 3) {

            response.setWarningLevel(
                    LicenseWarningLevel.DAYS_3.name()
            );

            response.setWarningMessage(
                    "GPLX sắp hết hạn trong 3 ngày"
            );

            return;
        }

        // 15 ngày
        if (remainingDays <= 15) {

            response.setWarningLevel(
                    LicenseWarningLevel.DAYS_15.name()
            );

            response.setWarningMessage(
                    "GPLX sắp hết hạn trong 15 ngày"
            );

            return;
        }

        // 30 ngày
        if (remainingDays <= 30) {

            response.setWarningLevel(
                    LicenseWarningLevel.DAYS_30.name()
            );

            response.setWarningMessage(
                    "GPLX sắp hết hạn trong 30 ngày"
            );

            return;
        }


        // bình thường
        response.setWarningLevel(
                LicenseWarningLevel.NORMAL.name()
        );

        response.setWarningMessage(
                "GPLX còn hiệu lực"
        );
    }
}
