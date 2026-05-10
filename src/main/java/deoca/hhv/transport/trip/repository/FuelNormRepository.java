package deoca.hhv.transport.trip.repository;

import deoca.hhv.transport.trip.entity.FuelNorm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuelNormRepository extends JpaRepository<FuelNorm, String> {

    boolean existsByVehicleIdAndPurposeId(
            String vehicleId,
            String purposeId
    );

    Optional<FuelNorm> findByVehicleIdAndPurposeId(
            String vehicleId,
            String purposeId
    );
}
