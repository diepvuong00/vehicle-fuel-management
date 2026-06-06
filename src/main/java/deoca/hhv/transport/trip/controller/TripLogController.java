package deoca.hhv.transport.trip.controller;

import deoca.hhv.transport.trip.dto.request.TripCreateRequest;
import deoca.hhv.transport.trip.dto.response.TripResponse;
import deoca.hhv.transport.trip.service.TripLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripLogController {

    private final TripLogService tripLogService;

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
}
