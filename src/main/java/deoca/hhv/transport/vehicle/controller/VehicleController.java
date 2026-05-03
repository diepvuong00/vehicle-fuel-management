package deoca.hhv.transport.vehicle.controller;

import deoca.hhv.transport.common.ApiResponse;
import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.vehicle.dto.VehicleRequest;
import deoca.hhv.transport.vehicle.dto.VehicleResponse;
import deoca.hhv.transport.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService theVehicleService;

    public VehicleController(VehicleService theVehicleService){
        this.theVehicleService = theVehicleService;
    }

//    1. Thêm mới phương tiện
    @PostMapping
    public ApiResponse<VehicleResponse> create(
            @Valid @RequestBody VehicleRequest request) {

        return ApiResponse.success(theVehicleService.saveVehicle(request));
    }
    

//    2. Hiển thị danh sách phương tiện, phân trang
    @GetMapping
    public ApiResponse<PageResponse<VehicleResponse>> getAllVehicle(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String vehicleType
    ){
        return ApiResponse.success(
                theVehicleService.getVehicles(page, size, sort, keyword, vehicleType)
        );
    }

//    3.Hiển thị thông tin chi tiết của phương tiện

    @GetMapping("/{id}")
    public ApiResponse<VehicleResponse> getById(@PathVariable String id) {
        return ApiResponse.success(theVehicleService.getVehicleById(id));
    }

//    4.Update phương tiện theo id
    @PutMapping("/{id}")
    public ApiResponse<VehicleResponse> update(
            @PathVariable String id,
            @Valid @RequestBody VehicleRequest request) {
        return ApiResponse.success(theVehicleService.updateVehicle(id, request));
    }

//    5. Cập nhật một phần phương tiện
    @PatchMapping("/{id}")
    public ApiResponse<VehicleResponse> patch(
            @PathVariable String id,
            @RequestBody VehicleRequest request) {

        VehicleResponse response = theVehicleService.patchVehicle(id, request);
        return ApiResponse.success(response);
    }

//    6. Xóa phương tiện theo id
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        theVehicleService.deleteVehicle(id);
        return ApiResponse.success("Deleted successfully");
    }

//    7.Thay đổi trang thái hoạt động của phương tiện
    @PatchMapping("/{id}/status")
    public ApiResponse<?> updateStatus(
            @PathVariable String id,
            @RequestParam String status) {
        return ApiResponse.success(theVehicleService.updateStatus(id, status));
    }

}
