package deoca.hhv.transport.trip.repository;

import deoca.hhv.transport.trip.entity.FuelNorm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FuelNormRepository extends JpaRepository<FuelNorm, String> {

    boolean existsByVehicleIdAndPurposeId(
            String vehicleId,
            String purposeId
    );

    Optional<FuelNorm> findByVehicleIdAndPurposeId(
            String vehicleId,
            String purposeId
    );

    Page<FuelNorm> findByVehicleId(String vehicleId, Pageable pageable);

    Page<FuelNorm> findByPurposeId(String purposeId, Pageable pageable);

    Page<FuelNorm> findByVehicleIdAndPurposeId(String vehicleId, String purposeId, Pageable pageable);

    List<FuelNorm>
    findByVehicleIdAndActiveTrue(
            String vehicleId
    );
}
