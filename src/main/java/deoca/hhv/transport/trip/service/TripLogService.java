package deoca.hhv.transport.trip.service;

import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.trip.dto.request.TripCreateRequest;
import deoca.hhv.transport.trip.dto.response.TripResponse;

public interface TripLogService {

//    1.Thêm dòng nhật trình khi nạp nhiên liệu
    void addFuelIssueToTrip(
            FuelIssue fuelIssue
    );

//    2.Xem chi tiết dòng nhaatj trình
    TripResponse getById(
            String tripId
    );

//    3. Tạo nhật trình tháng của phương tiện
    TripResponse createTrip(
            TripCreateRequest request
    );


}
