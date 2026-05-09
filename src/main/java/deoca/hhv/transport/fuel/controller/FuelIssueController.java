package deoca.hhv.transport.fuel.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.fuel.dto.reponse.FuelIssueResponse;
import deoca.hhv.transport.fuel.dto.request.FuelIssueRequest;
import deoca.hhv.transport.fuel.service.FuelIssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
