package deoca.hhv.transport.driver.repository.spec;

import deoca.hhv.transport.driver.entity.Driver;
import deoca.hhv.transport.driver.enums.DriverStatus;
import org.springframework.data.jpa.domain.Specification;

public class DriverSpecification {

    private DriverSpecification() {
    }

    public static Specification<Driver> hasKeyword(
            String keyword
    ) {

        return (root, query, cb) -> {

            if (keyword == null || keyword.isBlank()) {
                return null;
            }

            String search =
                    "%" + keyword.trim().toLowerCase() + "%";

            return cb.or(

                    cb.like(
                            cb.lower(root.get("fullName")),
                            search
                    ),

                    cb.like(
                            cb.lower(root.get("address")),
                            search
                    )

            );
        };
    }

    public static Specification<Driver> hasStatus(
            DriverStatus status
    ) {

        return (root, query, cb) -> {

            if (status == null) {
                return null;
            }

            return cb.equal(
                    root.get("status"),
                    status
            );
        };
    }

    public static Specification<Driver> isNotDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }
}
