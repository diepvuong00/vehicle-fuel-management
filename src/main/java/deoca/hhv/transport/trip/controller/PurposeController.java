package deoca.hhv.transport.trip.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.trip.dto.reponse.PurposeResponse;
import deoca.hhv.transport.trip.dto.request.PurposeRequest;
import deoca.hhv.transport.trip.service.PurposeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purposes")
public class PurposeController {

    private final PurposeService service;

    @PostMapping
    public ApiResponse<PurposeResponse> create(
            @Valid
            @RequestBody PurposeRequest request
    ) {

        return ApiResponse.<PurposeResponse>builder()
                .success(true)
                .message("Create purpose success")
                .data(
                        service.create(request)
                )
                .build();
    }

    @GetMapping
    public ApiResponse<List<PurposeResponse>> getAll() {
        return ApiResponse.<List<PurposeResponse>>builder()
                .success(true)
                .message("Get purposes success")
                .data(service.getAll())
                .build();
    }
}
