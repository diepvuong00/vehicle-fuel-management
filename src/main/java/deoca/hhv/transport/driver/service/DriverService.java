package deoca.hhv.transport.driver.service;

import deoca.hhv.transport.driver.dto.reponse.DriverResponse;
import deoca.hhv.transport.driver.dto.request.DriverRequest;
import org.springframework.data.domain.Page;

public interface DriverService {

//    1. Tạo mới tài xe
    DriverResponse createDriver(DriverRequest request);

//    2.Hiển thị danh sách tài xế và phân trang
    Page<DriverResponse> getDrivers(
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword
    );

//    3. Hiển thị chi tiết thông tin của một tài xế
//    DriverResponse findByIdAndDeletedFalse(String id);
    DriverResponse getDriverById(String id);
}
