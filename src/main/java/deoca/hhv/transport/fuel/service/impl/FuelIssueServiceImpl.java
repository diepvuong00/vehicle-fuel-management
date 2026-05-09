package deoca.hhv.transport.fuel.service.impl;

import deoca.hhv.transport.driver.entity.Driver;
import deoca.hhv.transport.driver.repository.DriverRepository;
import deoca.hhv.transport.fuel.dto.reponse.FuelIssueResponse;
import deoca.hhv.transport.fuel.dto.request.FuelIssueRequest;
import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.fuel.repository.FuelIssueRepository;
import deoca.hhv.transport.fuel.service.FuelIssueService;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import deoca.hhv.transport.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FuelIssueServiceImpl implements FuelIssueService {

    private final FuelIssueRepository fuelIssueRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

//    1.Thêm phiếu cấp phát
    @Override
    public FuelIssueResponse create(FuelIssueRequest request) {

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found"));

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

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

        fuelIssueRepository.save(fuelIssue);


        return mapResponse(fuelIssue);
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
}
