package deoca.hhv.transport.trip.repository;

import deoca.hhv.transport.trip.entity.TripLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripLogRepository
        extends JpaRepository<TripLog, String> {
    Optional<TripLog>
    findByVehicleIdAndMonthAndYear(
            String vehicleId,
            Integer month,
            Integer year
    );

    boolean existsByVehicle_IdAndMonthAndYear(
            String vehicleId,
            Integer month,
            Integer year
    );
}
