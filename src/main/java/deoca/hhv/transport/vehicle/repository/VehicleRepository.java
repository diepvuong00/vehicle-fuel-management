package deoca.hhv.transport.vehicle.repository;

import deoca.hhv.transport.vehicle.entity.Vehicle;
import deoca.hhv.transport.vehicle.enums.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String>,
        JpaSpecificationExecutor<Vehicle> {
    boolean existsByLicensePlate(String licensePlate);

    Optional<Vehicle>  findByIdAndDeletedFalse(String id);

    List<Vehicle> findByStatusAndDeletedFalse(
            VehicleStatus status);

    List<Vehicle> findByDeletedFalse();
}
