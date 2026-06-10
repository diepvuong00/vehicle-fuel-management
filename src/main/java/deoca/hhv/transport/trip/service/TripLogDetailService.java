package deoca.hhv.transport.trip.service;

import deoca.hhv.transport.trip.dto.request.TripLogDetailCreateRequest;
import deoca.hhv.transport.trip.dto.request.TripLogDetailUpdateRequest;
import deoca.hhv.transport.trip.dto.response.TripLogDetailDeleteResponse;
import deoca.hhv.transport.trip.dto.response.TripLogDetailResponse;

public interface TripLogDetailService {

    TripLogDetailResponse createDetail(
            String tripId,
            TripLogDetailCreateRequest request
    );

//    Lấy danh sách dòng nhật trình
    TripLogDetailResponse getDetails(
            String tripId
    );

//    Cập nhật dòng nhật trình
    TripLogDetailResponse updateDetail(
        String detailId,
        TripLogDetailUpdateRequest request
    );

//    Xóa dòng nhật trình
    TripLogDetailDeleteResponse deleteDetail(
            String detailId
    );



}
