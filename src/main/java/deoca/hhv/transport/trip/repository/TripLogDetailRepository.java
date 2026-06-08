package deoca.hhv.transport.trip.repository;

import deoca.hhv.transport.trip.entity.TripLogDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TripLogDetailRepository
        extends JpaRepository<TripLogDetail, String> {

    List<TripLogDetail>
    findByTripLogIdOrderByWorkDateAsc(
            String tripId
    );

    List<TripLogDetail>
    findByTripLogId(
            String tripId
    );


}
