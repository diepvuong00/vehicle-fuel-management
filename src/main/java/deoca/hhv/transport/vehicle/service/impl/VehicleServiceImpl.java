package deoca.hhv.transport.vehicle.service.impl;

import deoca.hhv.transport.audit.service.AuditService;
import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.common.storage.service.StorageService;
import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import deoca.hhv.transport.fuel.repository.FuelIssueRepository;
import deoca.hhv.transport.fuelnorm.repository.FuelNormRepository;
import deoca.hhv.transport.trip.repository.TripLogRepository;
import deoca.hhv.transport.vehicle.dto.VehicleRequest;
import deoca.hhv.transport.vehicle.dto.VehicleResponse;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import deoca.hhv.transport.vehicle.enums.VehicleStatus;
import deoca.hhv.transport.vehicle.repository.VehicleRepository;
import deoca.hhv.transport.vehicle.repository.spec.VehicleSpecification;
import deoca.hhv.transport.vehicle.service.VehicleService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    private final TripLogRepository tripLogRepository;

    private final FuelIssueRepository fuelIssueRepository;

    private final FuelNormRepository fuelNormRepository;

    private final ModelMapper mapper;
    private final AuditService auditService;
    private final StorageService storageService;

    public VehicleServiceImpl(VehicleRepository repository, TripLogRepository tripLogRepository, FuelIssueRepository fuelIssueRepository, FuelNormRepository fuelNormRepository, ModelMapper mapper, AuditService auditService, StorageService storageService) {
        this.repository = repository;
        this.tripLogRepository = tripLogRepository;
        this.fuelIssueRepository = fuelIssueRepository;
        this.fuelNormRepository = fuelNormRepository;
        this.mapper = mapper;
        this.auditService = auditService;
        this.storageService = storageService;
    }

//    1.Thêm mới phương tiện
    @Override
    public VehicleResponse saveVehicle(VehicleRequest request) {
//              1.Check biển số xe có trùng không
        if(repository.existsByLicensePlate(request.getLicensePlate())){
//            throw new RuntimeException("License plate already exists");
            throw new AppException(ErrorCode.VEHICLE_EXISTED);
        }
//              2. map Request → Entity (AUTO)
        Vehicle theVehicle = mapper.map(request, Vehicle.class);

//              2.1 Xử lý phần audit và booble delete
        theVehicle.setStatus(VehicleStatus.ACTIVE);
        theVehicle.setDeleted(false);
        theVehicle.setCreatedAt(LocalDateTime.now());

//              3. Lưu vào DB
        Vehicle saved = repository.save(theVehicle);

//              3.1 Audit
        auditService.log(
                "CREATE",
                "VEHICLE",
                saved.getId(),
                null, saved);

//              4. map Entity -> Response (AUTO)
        return mapper.map(saved, VehicleResponse.class);
    }

//    2. Hiển thị danh sách phương tiện có phân trang
    @Override
    public PageResponse<VehicleResponse> getVehicles(
            int page,
            int size,
            String sort,
            String keyword,
            String vehicleType,
            VehicleStatus status) {
//        2.1. Sort tách chuỗi thành tên cột và hướng sắp xếp, tạo Pageable để Spring Data Jpa hiểu
        String[] sortArr = sort.split(",");
        Sort.Direction direction = sortArr[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortArr[0]));

//        2.2. Sử dụng Specification để xây dựng câu lệnh sql. Ktra lọc theo Keyword hay VehicleType
//        Specification<Vehicle> spec = Specification
//                .where(VehicleSpecification.hasKeyword(keyword));
        Specification<Vehicle> spec = Specification
                .where(VehicleSpecification
                        .hasKeyword(keyword))
                .and(VehicleSpecification
                        .hasType(vehicleType))
                .and(VehicleSpecification
                        .hasStatus(status))
                .and((root, query, cb) ->
                        cb.equal(root.get("deleted"), false)
                );

//        2.3. Query. Gọi repo để lấy data. Kq trả về Page<Vehicle> chứa tất cả đối tượng
//        Page<Vehicle> vehiclePage = repository.findAll(
//                (root, query, cb) -> cb.equal(root.get("deleted"), false),
//                pageable
//        );
        Page<Vehicle> vehiclePage =
                repository.findAll(spec, pageable);
//        2.4. map DTO
        List<VehicleResponse> content = vehiclePage.getContent()
                .stream()
                .map(v -> mapper.map(v, VehicleResponse.class))
                .toList();

        return PageResponse.<VehicleResponse>builder()
                .content(content)
                .page(vehiclePage.getNumber())
                .size(vehiclePage.getSize())
                .totalElements(vehiclePage.getTotalElements())
                .totalPages(vehiclePage.getTotalPages())
                .build();
    }

    //    3.hiển thị chi tiết thông tin của 1 phương tiện
    @Override
    public VehicleResponse getVehicleById(String id) {
        Vehicle vehicle = repository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        VehicleResponse response = mapper.map(vehicle, VehicleResponse.class);

        response.setImageKey(storageService.getImageUrl(vehicle.getImageKey()));

        return response;
//        return mapper.map(vehicle, VehicleResponse.class);
    }

//    4. Xóa phương tiện theo Id
//    @Override
//    public void deleteVehicle(String id) {
//        Vehicle vehicle = repository.findByIdAndDeletedFalse(id)
//                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));
//
//        vehicle.setDeleted(true);
//        vehicle.setStatus(VehicleStatus.INACTIVE);
//
//        repository.save(vehicle);
//
//        auditService.log("DELETE", "VEHICLE", id, vehicle, null);
//    }

    @Override
    @Transactional
    public void deleteVehicle(
            String id
    ) {

        Vehicle vehicle =
                repository
                        .findByIdAndDeletedFalse(id)
                        .orElseThrow(
                                () ->
                                        new AppException(
                                                ErrorCode.VEHICLE_NOT_FOUND
                                        )
                        );

        boolean usedInTrip =
                tripLogRepository
                        .existsByVehicleId(id);

        boolean usedInFuelIssue =
                fuelIssueRepository
                        .existsByVehicleId(id);

        boolean usedInNorm =
                fuelNormRepository
                        .existsByVehicleId(id);

        if (
                usedInTrip
                        ||
                        usedInFuelIssue
                        ||
                        usedInNorm
        ) {

            throw new AppException(
                    ErrorCode.VEHICLE_IN_USE
            );
        }

        vehicle.setDeleted(true);

        repository.save(vehicle);

        auditService.log(
                "DELETE",
                "VEHICLE",
                id,
                vehicle,
                null
        );
    }

    @Transactional
    public void deactivate(
            String id
    ) {

        Vehicle vehicle =
                repository
                        .findByIdAndDeletedFalse(id)
                        .orElseThrow(
                                () ->
                                        new AppException(
                                                ErrorCode.VEHICLE_NOT_FOUND
                                        )
                        );

        vehicle.setStatus(
                VehicleStatus.INACTIVE
        );

        repository.save(vehicle);
    }

//    5. Update phương tiện theo Id
    @Override
    public VehicleResponse updateVehicle(String id, VehicleRequest request) {
        Vehicle vehicle = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        Vehicle oldData = new Vehicle();
        BeanUtils.copyProperties(vehicle, oldData);

        mapper.map(request, vehicle);
        vehicle.setUpdatedAt(LocalDateTime.now());

        Vehicle updated = repository.save(vehicle);

        auditService.log("UPDATE", "VEHICLE", id, oldData, updated);

        return mapper.map(updated, VehicleResponse.class);
    }

//    6. Cập nhật trang thái của phương tiện
    @Override
    public VehicleResponse updateStatus(String id, String status) {

        Vehicle vehicle = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        VehicleStatus newStatus = VehicleStatus.valueOf(status.toUpperCase());

        VehicleStatus oldStatus = vehicle.getStatus();

        vehicle.setStatus(newStatus);

        Vehicle updated = repository.save(vehicle);

        auditService.log("UPDATE_STATUS", "VEHICLE", id, oldStatus, newStatus);

        return mapper.map(updated, VehicleResponse.class);
    }

//    7. Cập nhật một phần phương tiện
    @Override
    public VehicleResponse patchVehicle(String id, VehicleRequest request) {

        // 1. Tìm xe hiện tại, nếu không thấy thì ném lỗi
        Vehicle vehicle = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

            // 2. Sao lưu dữ liệu cũ để phục vụ Audit Log
        Vehicle oldData = new Vehicle();
        BeanUtils.copyProperties(vehicle, oldData);

        // 3. Ánh xạ các trường không null từ request vào đối tượng entity hiện tại
        // Nhờ cấu hình setSkipNullEnabled(true), các trường null trong request sẽ không ghi đè dữ liệu cũ
        mapper.map(request, vehicle);

        // 4. Cập nhật thời gian sửa đổi
        vehicle.setUpdatedAt(LocalDateTime.now());

        // 5. Lưu vào Database
        Vehicle updated = repository.save(vehicle);

        // 6. Ghi log thay đổi (Sử dụng AuditService bạn đã tạo)
        auditService.log("PATCH_UPDATE", "VEHICLE", id, oldData, updated);

        // 7. Trả về kết quả
        return mapper.map(updated, VehicleResponse.class);
    }

    @Override
    @Transactional
    public String uploadImage(String id, MultipartFile file) {
        Vehicle vehicle=
                repository
                        .findById(id)
                        .orElseThrow(
                                ()->new AppException(ErrorCode.VEHICLE_NOT_FOUND)
                        );

        String imageKey=
                storageService
                        .uploadVehicleImage(
                                file
                        );
        vehicle.setImageKey(
                imageKey
        );

        repository.save(vehicle);

        return imageKey;
    }

}
