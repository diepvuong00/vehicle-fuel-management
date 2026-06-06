package deoca.hhv.transport.trip.service;

import deoca.hhv.transport.trip.dto.request.TripLogDetailCreateRequest;
import deoca.hhv.transport.trip.dto.response.TripLogDetailResponse;

public interface TripLogDetailService {

    TripLogDetailResponse createDetail(
            String tripId,
            TripLogDetailCreateRequest request
    );
}
