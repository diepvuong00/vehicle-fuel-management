package deoca.hhv.transport.trip.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.trip.dto.reponse.FuelNormResponse;
import deoca.hhv.transport.trip.dto.request.FuelNormRequest;
import deoca.hhv.transport.trip.service.FuelNormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ApiResponse<PageResponse<FuelNormResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) String purposeId
    ) {
        return ApiResponse.<PageResponse<FuelNormResponse>>builder()
                .success(true)
                .message("Get fuel norms success")
                .data(fuelNormService.getAll(page, size, vehicleId, purposeId))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<FuelNormResponse> getById(@PathVariable String id) {
        return ApiResponse.<FuelNormResponse>builder()
                .success(true)
                .message("Get fuel norm success")
                .data(fuelNormService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<FuelNormResponse> update(
            @PathVariable String id,
            @Valid @RequestBody FuelNormRequest request
    ) {
        return ApiResponse.<FuelNormResponse>builder()
                .success(true)
                .message("Update fuel norm success")
                .data(fuelNormService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        fuelNormService.delete(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Delete fuel norm success")
                .build();
    }
}
