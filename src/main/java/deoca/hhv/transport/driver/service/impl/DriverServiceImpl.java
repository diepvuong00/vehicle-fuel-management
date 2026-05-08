package deoca.hhv.transport.driver.service.impl;

import deoca.hhv.transport.audit.service.AuditService;
import deoca.hhv.transport.driver.dto.reponse.DriverLicenseResponse;
import deoca.hhv.transport.driver.dto.reponse.DriverResponse;
import deoca.hhv.transport.driver.dto.request.DriverRequest;
import deoca.hhv.transport.driver.entity.Driver;
import deoca.hhv.transport.driver.entity.DriverLicense;
import deoca.hhv.transport.driver.entity.LicenseStatus;
import deoca.hhv.transport.driver.repository.DriverRepository;
import deoca.hhv.transport.driver.service.DriverService;
import deoca.hhv.transport.driver.util.LicenseWarningUtil;
import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
//@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository repository;
    private final ModelMapper mapper;
    private final AuditService auditService;

    public DriverServiceImpl(DriverRepository repository, ModelMapper mapper, AuditService auditService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditService = auditService;

        this.mapper.getConfiguration().setSkipNullEnabled(true);
        this.mapper.typeMap(DriverRequest.class, Driver.class)
                .addMappings(m -> m.skip(Driver::setId));
    }

    //    1.Thêm mới tài xế
    @Override
    public DriverResponse createDriver(DriverRequest request) {

        // 1. CHECK TRÙNG CCCD
        if (repository.existsByNationalId(request.getNationalId())) {
            throw new AppException(ErrorCode.NATIONAL_ID_EXISTED);
        }

        // 2. Map entity
        Driver driver = mapper.map(request, Driver.class);

        // 3. Generate code
        driver.setCode("TX" + System.currentTimeMillis());

        // 4. Audit
        driver.setCreatedAt(LocalDateTime.now());
        driver.setUpdatedAt(LocalDateTime.now());

        // 5. Set license
        if (request.getLicenses() != null) {
            List<DriverLicense> licenses = request.getLicenses().stream().map(l -> {
                DriverLicense license = mapper.map(l, DriverLicense.class);

                // auto status
                if (license.getExpiryDate().isBefore(LocalDate.now())) {
                    license.setStatus(LicenseStatus.EXPIRED);
                } else {
                    license.setStatus(LicenseStatus.VALID);
                }

                license.setDriver(driver);
                return license;
            }).toList();

            driver.setLicenses(licenses);
        }

        // 6. Save
        repository.save(driver);

        // 7. Return response
        return mapToResponse(driver);
    }

    private DriverResponse mapToResponse(Driver driver) {
        DriverResponse response = mapper.map(driver, DriverResponse.class);

        if (driver.getLicenses() != null) {
            List<DriverLicenseResponse> licenses =
                    driver.getLicenses()
                            .stream()
//                            .map(l -> mapper.map(l, DriverLicenseResponse.class))
                            .map(this::mapLicenseResponse)
                            .toList();

            response.setLicenses(licenses);
        }

        return response;
    }

    @Override
    public Page<DriverResponse> getDrivers(
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Driver> driverPage;

        if (keyword != null && !keyword.isBlank()) {

            driverPage = repository
                    .findByDeletedFalseAndFullNameContainingIgnoreCase(
                            keyword,
                            pageable
                    );

        } else {

            driverPage = repository
                    .findByDeletedFalse(pageable);
        }
        return driverPage.map(this::mapToResponse);
    }

    @Override
    public DriverResponse getDriverById(String id) {

        Driver driver = repository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new AppException(ErrorCode.DRIVER_NOT_FOUND));

        return mapToResponse(driver);
    }

    private DriverLicenseResponse mapLicenseResponse(
            DriverLicense license
    ) {

        DriverLicenseResponse response =
                mapper.map(
                        license,
                        DriverLicenseResponse.class
                );

        // xử lý warning
        LicenseWarningUtil.setLicenseWarning(
                license,
                response
        );

        return response;
    }

    private String getLicenseStatusText(Enum<?> status) {

        return switch (status.name()) {

            case "ACTIVE" -> "Đang làm";

            case "INACTIVE" -> "Đã nghỉ";

            case "SUSPENDED" -> "Tạm nghỉ";

            default -> "Không xác định";
        };
    }


    // 2. CHECK TRÙNG SĐT
//        if (repository.existsByPhone(request.getPhone())) {
//            throw new AppException(ErrorCode.PHONE_ALREADY_EXISTS);
//        }

        // 3. MAP REQUEST → ENTITY
//        Driver driver = mapper.map(request, Driver.class);

        // 4. SET DEFAULT VALUE
//        driver.setCode(generateDriverCode());
//        driver.setDeleted(false);
//        driver.setCreatedAt(LocalDateTime.now());
//        driver.setStatus(request.getStatus());

        // 5. SAVE
//        Driver savedDriver = repository.save(driver);

        // 6. AUDIT LOG
//        auditService.log(
//                "CREATE",
//                "DRIVER",
//                savedDriver.getId(),
//                null,
//                savedDriver.getFullName()
//        );

        // 7. MAP RESPONSE
//        return mapper.map(savedDriver, DriverResponse.class);
        

//    }

    //Tạo ra mã tài xế Vd: TX-001, không trùng lặp
//    private String generateDriverCode() {
//        return "TX-" + System.currentTimeMillis();
//    }


}
