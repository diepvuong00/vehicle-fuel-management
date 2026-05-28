package deoca.hhv.transport.vehicle.repository.spec;

import deoca.hhv.transport.vehicle.entity.Vehicle;
import deoca.hhv.transport.vehicle.enums.VehicleStatus;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification {

    public static Specification<Vehicle> hasKeyword(String keyword) {

        return (root, query, cb) -> {

            if(keyword == null || keyword.trim().isEmpty())
                return null;

                String search =
                        "%" + keyword.toLowerCase() + "%";

                return cb.or(

                        cb.like(
                                cb.lower(root.get("licensePlate")),
                                search
                        ),

                        cb.like(
                                cb.lower(root.get("brand")),
                                search
                        ),

                        cb.like(
                                cb.lower(root.get("vehicleType")),
                                search
                        )
                );
            };
        }

    public static Specification<Vehicle>
        hasStatus(VehicleStatus status){

        return (root, query, cb)->{

            if(status == null)
                return null;

            return cb.equal(
                    root.get("status"),
                    status
            );

        };

    }


    public static Specification<Vehicle> hasType(
                String vehicleType
        ){

            return (root,query,cb)->{

                if(vehicleType == null
                        || vehicleType.isBlank())
                    return null;

                return cb.equal(
                        root.get("vehicleType"),
                        vehicleType
                );

            };
        }

    }

