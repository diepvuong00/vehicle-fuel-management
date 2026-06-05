package deoca.hhv.transport.driver.service;

import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.driver.dto.reponse.DriverResponse;
import deoca.hhv.transport.driver.dto.request.DriverRequest;
import deoca.hhv.transport.driver.enums.DriverStatus;
import org.springframework.data.domain.Page;

public interface DriverService {

    //    1. Tạo mới tài xe
    DriverResponse createDriver(DriverRequest request);

    //    2.Hiển thị danh sách tài xế và phân trang
    PageResponse<DriverResponse> getDrivers(
            int page,
            int size,
            String sort,
            String keyword,
            DriverStatus status
    );

    //    3. Hiển thị chi tiết thông tin của một tài xế
//    DriverResponse findByIdAndDeletedFalse(String id);
    DriverResponse getDriverById(String id);

    DriverResponse updateDriver(String id, DriverRequest request);

    void deleteDriver(String id);
}
