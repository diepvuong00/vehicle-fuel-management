package deoca.hhv.transport.trip.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.trip.dto.reponse.FuelNormResponse;
import deoca.hhv.transport.trip.dto.request.FuelNormRequest;
import deoca.hhv.transport.trip.service.FuelNormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fuel-norms")
public class FuelNormController {

    public final FuelNormService fuelNormService;

    @PostMapping
    public ApiResponse<FuelNormResponse> create(
            @Valid
            @RequestBody FuelNormRequest request
    ) {

        return ApiResponse.<FuelNormResponse>builder()
                .success(true)
                .message("Create fuel norm success")
                .data(
                        fuelNormService.create(request)
                )
                .build();
    }
}
