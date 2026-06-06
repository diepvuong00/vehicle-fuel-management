package deoca.hhv.transport.trip.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.trip.dto.request.TripCreateRequest;
import deoca.hhv.transport.trip.dto.request.TripSearchRequest;
import deoca.hhv.transport.trip.dto.response.TripResponse;
import deoca.hhv.transport.trip.dto.response.TripSummaryResponse;
import deoca.hhv.transport.trip.service.TripLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripLogController {

    private final TripLogService tripLogService;

//    1.Thêm nhật trình tháng
    @PostMapping
    public ResponseEntity<TripResponse> createTrip(

            @Valid
            @RequestBody
            TripCreateRequest request
    ) {

        return ResponseEntity.ok(
                tripLogService.createTrip(
                        request
                )
        );
    }

//    2.Filter auto
    @GetMapping
    public ApiResponse<List<TripSummaryResponse>>
        searchTrips(

        @RequestParam(required = false)
        String vehicleId,

        @RequestParam(required = false)
        Integer month,

        @RequestParam(required = false)
        Integer year,

        @RequestParam(required = false)
        Boolean closed,

        @RequestParam(required = false)
        String driverId
    ) {

        TripSearchRequest request =
            new TripSearchRequest();

        request.setVehicleId(vehicleId);
        request.setMonth(month);
        request.setYear(year);
        request.setClosed(closed);
        request.setDriverId(driverId);

        return ApiResponse
                .<List<TripSummaryResponse>>builder()
                .data(
                        tripLogService.searchTrips(
                            request
                    )
                )
                .build();
}

}
