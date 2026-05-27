package deoca.hhv.transport.vehicle.service;

import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.vehicle.dto.VehicleRequest;
import deoca.hhv.transport.vehicle.dto.VehicleResponse;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VehicleService {
//    1.Thêm mới phương tiện
    VehicleResponse saveVehicle(VehicleRequest theVehicleRequest);
//    2.Hiển thị danh sách phương tiện

    PageResponse<VehicleResponse> getVehicles(
            int page,
            int size,
            String sort,
            String keyword,
            String vehicleType
    );

//    3. Hiển thị chi tiết thông tin của một phương tiện
    VehicleResponse getVehicleById(String id);

//    4. Xóa phương tiện theo Id
    void deleteVehicle(String id);

//    5. Update phương tiện theo Id
    VehicleResponse updateVehicle(String id, VehicleRequest request);

//    6.Cập nhật trạng thái của phương tiện
    VehicleResponse updateStatus(String id, String status);

//    7. Cập nhật một phần của phương tiện
    VehicleResponse patchVehicle(String id, VehicleRequest request);

//    8. Thêm ảnh phương tiện
    String uploadImage(String id, MultipartFile file);
}

