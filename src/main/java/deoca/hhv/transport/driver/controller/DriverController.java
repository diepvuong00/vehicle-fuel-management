package deoca.hhv.transport.driver.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.driver.dto.reponse.DriverResponse;
import deoca.hhv.transport.driver.dto.request.DriverRequest;
import deoca.hhv.transport.driver.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService service;

    @PostMapping
    public ResponseEntity<ApiResponse<DriverResponse>> createDriver(
            @Valid @RequestBody DriverRequest request
    ) {

        DriverResponse response = service.createDriver(request);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
