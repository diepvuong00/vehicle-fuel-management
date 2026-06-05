package deoca.hhv.transport.trip.repository;

import deoca.hhv.transport.trip.entity.TripLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripLogRepository extends JpaRepository<TripLog, String> {
    Optional<TripLog>
    findByVehicleIdAndMonthAndYear(
            String vehicleId,
            Integer month,
            Integer year
    );
}
