package deoca.hhv.transport.vehicle.repository.spec;

import deoca.hhv.transport.vehicle.entity.Vehicle;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification {

    public static Specification<Vehicle> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isEmpty()) return null;

            return cb.or(
                    cb.like(root.get("licensePlate"), "%" + keyword + "%"),
                    cb.like(root.get("brand"), "%" + keyword + "%")
            );
        };
    }

    public static Specification<Vehicle> hasType(String vehicleType) {
        return (root, query, cb) -> {
            if (vehicleType == null || vehicleType.isEmpty()) return null;
            return cb.equal(root.get("vehicleType"), vehicleType);
        };
    }
}
