package deoca.hhv.transport.trip.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.trip.dto.request.TripLogDetailCreateRequest;
import deoca.hhv.transport.trip.dto.response.TripLogDetailResponse;
import deoca.hhv.transport.trip.dto.response.TripResponse;
import deoca.hhv.transport.trip.service.TripLogDetailService;
import deoca.hhv.transport.trip.service.TripLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripLogDetailController {

    private final TripLogDetailService tripLogDetailService;

    private final TripLogService tripLogService;

//    1. Thêm dòng nhật trình
    @PostMapping("/{tripId}/details")
    public ApiResponse<TripLogDetailResponse>
    createDetail(

            @PathVariable
            String tripId,

            @RequestBody
            @Valid
            TripLogDetailCreateRequest request
    ) {

        return ApiResponse.<TripLogDetailResponse>
                        builder()
                .data(
                        tripLogDetailService
                                .createDetail(
                                        tripId,
                                        request
                                )
                )
                .build();
    }


    @GetMapping("/{tripId}/details")
    public ApiResponse<TripLogDetailResponse> getDetails(
            @PathVariable String tripId
    ) {

        return ApiResponse
                .<TripLogDetailResponse>builder()
                .success(true)
                .data(
                        tripLogDetailService
                                .getDetails(
                                        tripId
                                )
                )
                .build();
    }

}
