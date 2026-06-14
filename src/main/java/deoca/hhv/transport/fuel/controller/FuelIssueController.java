package deoca.hhv.transport.fuel.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.fuel.dto.reponse.FuelIssueResponse;
import deoca.hhv.transport.fuel.dto.request.FuelIssueCancelRequest;
import deoca.hhv.transport.fuel.dto.request.FuelIssueRequest;
import deoca.hhv.transport.fuel.service.FuelIssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/fuel-issues")
@RequiredArgsConstructor
public class FuelIssueController {

    private final FuelIssueService fuelIssueService;

    @PostMapping
    public ApiResponse<FuelIssueResponse> create(
            @Valid @RequestBody FuelIssueRequest request
    ) {

        return ApiResponse.<FuelIssueResponse>builder()
                .success(true)
                .message("Create fuel issue success")
                .data(fuelIssueService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<FuelIssueResponse>> getAll(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(required = false)
            String status,

            @RequestParam(required = false)
            String vehicleId,

            @RequestParam(required = false)
            String driverId,

            @RequestParam(required = false)
            String keyword
    ) {

        return ApiResponse
                .<PageResponse<FuelIssueResponse>>builder()
                .success(true)
                .message("Get fuel issues success")
                .data(
                        fuelIssueService.getAll(
                                page,
                                size,
                                status,
                                vehicleId,
                                driverId,
                                keyword
                        )
                )
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<FuelIssueResponse> update(
            @PathVariable String id,
            @Valid @RequestBody FuelIssueRequest request
    ) {
        return ApiResponse.<FuelIssueResponse>builder()
                .success(true)
                .message("Update fuel issue success")
                .data(fuelIssueService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable String id
    ) {
        fuelIssueService.delete(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Delete fuel issue success")
                .build();
    }

    @PostMapping(
            "/{fuelIssueId}/cancel"
    )
    public ApiResponse<Void> cancelFuelIssue(
            @PathVariable String fuelIssueId,

            @Valid
            @RequestBody
            FuelIssueCancelRequest request
    ) {

        fuelIssueService.cancelFuelIssue(
                fuelIssueId,
                request
        );

        return ApiResponse.<Void>builder()
                .success(true)
                .message(
                        "Hủy phiếu cấp phát thành công"
                )
                .code(200)
                .timestamp(
                        LocalDateTime.now()
                )
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<FuelIssueResponse> getById(@PathVariable String id) {
        return ApiResponse.<FuelIssueResponse>builder()
                .success(true)
                .message("Get fuel issue success")
                .data(fuelIssueService.getById(id))
                .build();
    }
}
