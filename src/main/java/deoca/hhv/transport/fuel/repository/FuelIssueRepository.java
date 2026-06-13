package deoca.hhv.transport.fuel.repository;

import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.fuel.enums.FuelIssueStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuelIssueRepository extends JpaRepository<FuelIssue, String> {

    Page<FuelIssue> findByStatus(
            FuelIssueStatus status,
            Pageable pageable
    );

    Page<FuelIssue> findByVehicleId(
            String vehicleId,
            Pageable pageable
    );

    Page<FuelIssue> findByDriverId(
            String driverId,
            Pageable pageable
    );

    Page<FuelIssue> findByIssueCodeContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );


    Optional<FuelIssue>
    findTopByVehicleIdOrderByFuelTimeDesc(
            String vehicleId
    );

    boolean existsByVehicleId(
            String vehicleId
    );
}
