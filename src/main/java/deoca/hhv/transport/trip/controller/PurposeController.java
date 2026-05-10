package deoca.hhv.transport.trip.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.trip.dto.reponse.PurposeResponse;
import deoca.hhv.transport.trip.dto.request.PurposeRequest;
import deoca.hhv.transport.trip.service.PurposeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
