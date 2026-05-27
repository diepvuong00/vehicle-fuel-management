package deoca.hhv.transport.driver.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.driver.dto.reponse.DriverResponse;
import deoca.hhv.transport.driver.dto.request.DriverRequest;
import deoca.hhv.transport.driver.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService service;

    //    1.Thêm mới tài xế
    @PostMapping
    public ResponseEntity<ApiResponse<DriverResponse>> createDriver(
            @Valid @RequestBody DriverRequest request
    ) {

        DriverResponse response = service.createDriver(request);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    //    2.Hiển thị danh sách phương tiện
    @GetMapping
    public ApiResponse<Page<DriverResponse>> getDrivers(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "createdAt")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String direction,

            @RequestParam(required = false)
            String keyword
    ) {

        return ApiResponse.<Page<DriverResponse>>builder()
                .success(true)
                .message("Lấy danh sách tài xế thành công")
                .data(
                        service.getDrivers(
                                page,
                                size,
                                sortBy,
                                direction,
                                keyword
                        )
                )
                .timestamp(LocalDateTime.now())
                .code(200)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DriverResponse> updateDriver(
            @PathVariable String id,
            @Valid @RequestBody DriverRequest request
    ) {
        return ApiResponse.<DriverResponse>builder()
                .success(true)
                .message("Cập nhật tài xế thành công")
                .data(service.updateDriver(id, request))
                .timestamp(LocalDateTime.now())
                .code(200)
                .build();
    }

    //    3. Hiển thị chi tiết thông tin của tài xế
    @GetMapping("/{id}")
    public ApiResponse<DriverResponse> getDriverById(
            @PathVariable String id
    ) {

        return ApiResponse.<DriverResponse>builder()
                .success(true)
                .message("Lấy thông tin tài xế thành công")
                .data(service.getDriverById(id))
                .timestamp(LocalDateTime.now())
                .code(200)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDriver(@PathVariable String id) {
        service.deleteDriver(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa tài xế thành công")
                .timestamp(LocalDateTime.now())
                .code(200)
                .build();
    }

}
