package deoca.hhv.transport.trip.service;

import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.trip.dto.request.TripCreateRequest;
import deoca.hhv.transport.trip.dto.request.TripSearchRequest;
import deoca.hhv.transport.trip.dto.response.*;

import java.util.List;

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

//    4.Filter tự động list trip log theo tháng nawm..
    List<TripSummaryResponse> searchTrips(
        TripSearchRequest request
    )   ;

//    5.Chi tiết nhật trình
    TripDetailResponse getTripDetail(
        String tripId
    );

//    6. Chốt tháng của nhật trình
    TripCloseResponse closeTrip(
            String tripId
    );

}
