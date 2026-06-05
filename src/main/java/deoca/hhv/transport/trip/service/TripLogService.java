package deoca.hhv.transport.trip.service;

import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.trip.dto.response.TripResponse;

public interface TripLogService {

//    1.Thêm dòng nhật trình khi nạp nhiên liệu
    void addFuelIssueToTrip(
            FuelIssue fuelIssue
    );

    TripResponse getById(
            String tripId
    );
}
