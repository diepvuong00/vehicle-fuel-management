package deoca.hhv.transport.fuel.service.impl;

import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.driver.entity.Driver;
import deoca.hhv.transport.driver.enums.DriverStatus;
import deoca.hhv.transport.driver.repository.DriverRepository;
import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import deoca.hhv.transport.fuel.dto.reponse.FuelIssueResponse;
import deoca.hhv.transport.fuel.dto.request.FuelIssueRequest;
import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.fuel.enums.FuelIssueStatus;
import deoca.hhv.transport.fuel.repository.FuelIssueRepository;
import deoca.hhv.transport.fuel.service.FuelIssueService;
import deoca.hhv.transport.trip.event.FuelIssueCreatedEvent;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import deoca.hhv.transport.vehicle.enums.VehicleStatus;
import deoca.hhv.transport.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FuelIssueServiceImpl implements FuelIssueService {

    private final FuelIssueRepository fuelIssueRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    private final ApplicationEventPublisher publisher;

    //    1.Thêm phiếu cấp phát
    @Override
    public FuelIssueResponse create(FuelIssueRequest request) {

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy phương tiện"));
        if (
                vehicle.getStatus()
                        !=
                        VehicleStatus.ACTIVE
        ) {

            throw new AppException(
                    ErrorCode.VEHICLE_INACTIVE
            );
        }


        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy tài xế"));
        if (
                driver.getStatus()
                        !=
                        DriverStatus.ACTIVE
        ) {

            throw new AppException(
                    ErrorCode.DRIVER_INACTIVE
            );
        }

//        if(
//                request.getCurrentKm()
//                        <
//                        vehicle.getCurrentKm()
//        ){
//
//            throw new AppException(
//                    ErrorCode.DRIVER_INACTIVE
//            );
//        }

        FuelIssue fuelIssue = FuelIssue.builder()
                .issueCode(generateCode())
                .vehicle(vehicle)
                .driver(driver)
                .fuelType(request.getFuelType())
                .quantity(request.getQuantity())
                .unit(request.getUnit())
                .currentKm(request.getCurrentKm())
                .fuelStation(request.getFuelStation())
                .requestedBy(request.getRequestedBy())
                .fuelTime(
                        request.getFuelTime() != null
                                ? request.getFuelTime()
                                : LocalDateTime.now()
                )
                .note(request.getNote())
                .build();


        // 1. Thêm "FuelIssue savedFuelIssue =" ở đầu dòng để tạo biến hứng dữ liệu sau khi lưu thành công
        FuelIssue savedFuelIssue = fuelIssueRepository.save(fuelIssue);

        // 2. Bắn sự kiện kèm theo biến vừa hứng được
        publisher.publishEvent(
                new FuelIssueCreatedEvent(savedFuelIssue)
        );

        // 3. return cho chuẩn dữ liệu đã lưu xuống DB
        return mapResponse(savedFuelIssue);
    }

    private FuelIssueResponse mapResponse(FuelIssue fuelIssue) {

        return FuelIssueResponse.builder()
                .id(fuelIssue.getId())
                .issueCode(fuelIssue.getIssueCode())

                .vehicleId(fuelIssue.getVehicle().getId())
                .vehiclePlate(fuelIssue.getVehicle().getLicensePlate())

                .driverId(fuelIssue.getDriver().getId())
                .driverName(fuelIssue.getDriver().getFullName())

                .fuelType(fuelIssue.getFuelType().name())
                .quantity(fuelIssue.getQuantity())
                .unit(fuelIssue.getUnit())
                .currentKm(fuelIssue.getCurrentKm())
                .fuelStation(fuelIssue.getFuelStation())
                .requestedBy(fuelIssue.getRequestedBy())
                .fuelTime(fuelIssue.getFuelTime())
                .note(fuelIssue.getNote())

                .status(fuelIssue.getStatus().name())

                .approvedBy(fuelIssue.getApprovedBy())
                .approvedAt(fuelIssue.getApprovedAt())

                .createdAt(fuelIssue.getCreatedAt())
                .build();
    }

    private String generateCode() {
        return "FI-" + System.currentTimeMillis();
    }

//    2.Hiển thị danh sách phiếu cap phat

    @Override
    public PageResponse<FuelIssueResponse> getAll(
            int page,
            int size,
            String status,
            String vehicleId,
            String driverId,
            String keyword
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<FuelIssue> fuelIssuePage;

        /*
         * FILTER STATUS
         */
        if (status != null && !status.isBlank()) {

            fuelIssuePage =
                    fuelIssueRepository.findByStatus(
                            FuelIssueStatus.valueOf(status),
                            pageable
                    );
        }

        /*
         * FILTER VEHICLE
         */
        else if (vehicleId != null && !vehicleId.isBlank()) {

            fuelIssuePage =
                    fuelIssueRepository.findByVehicleId(
                            vehicleId,
                            pageable
                    );
        }

        /*
         * FILTER DRIVER
         */
        else if (driverId != null && !driverId.isBlank()) {

            fuelIssuePage =
                    fuelIssueRepository.findByDriverId(
                            driverId,
                            pageable
                    );
        }

        /*
         * SEARCH CODE
         */
        else if (keyword != null && !keyword.isBlank()) {

            fuelIssuePage =
                    fuelIssueRepository
                            .findByIssueCodeContainingIgnoreCase(
                                    keyword,
                                    pageable
                            );
        }

        /*
         * GET ALL
         */
        else {

            fuelIssuePage =
                    fuelIssueRepository.findAll(pageable);
        }

        List<FuelIssueResponse> responses =
                fuelIssuePage.getContent()
                        .stream()
                        .map(this::mapResponse)
                        .toList();

        return PageResponse.<FuelIssueResponse>builder()
                .content(responses)
                .page(fuelIssuePage.getNumber())
                .size(fuelIssuePage.getSize())
                .totalElements(fuelIssuePage.getTotalElements())
                .totalPages(fuelIssuePage.getTotalPages())
                .last(fuelIssuePage.isLast())
                .build();
    }

    @Override
    public FuelIssueResponse getById(String id) {
        FuelIssue fuelIssue = fuelIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuel issue not found"));
        return mapResponse(fuelIssue);
    }

    @Override
    public FuelIssueResponse update(String id, FuelIssueRequest request) {
        FuelIssue fuelIssue = fuelIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuel issue not found"));

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        fuelIssue.setVehicle(vehicle);
        fuelIssue.setDriver(driver);
        fuelIssue.setFuelType(request.getFuelType());
        fuelIssue.setQuantity(request.getQuantity());
        fuelIssue.setUnit(request.getUnit());
        fuelIssue.setCurrentKm(request.getCurrentKm());
        fuelIssue.setFuelStation(request.getFuelStation());
        fuelIssue.setRequestedBy(request.getRequestedBy());
        if (request.getFuelTime() != null) {
            fuelIssue.setFuelTime(request.getFuelTime());
        }
        fuelIssue.setNote(request.getNote());

        fuelIssueRepository.save(fuelIssue);

        return mapResponse(fuelIssue);
    }

    @Override
    public void delete(String id) {
        if (!fuelIssueRepository.existsById(id)) {
            throw new RuntimeException("Fuel issue not found");
        }
        fuelIssueRepository.deleteById(id);
    }
}
