package deoca.hhv.transport.fuelnorm.service.impl;

import deoca.hhv.transport.common.PageResponse;
import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import deoca.hhv.transport.fuelnorm.dto.reponse.FuelNormResponse;
import deoca.hhv.transport.fuelnorm.dto.request.FuelNormRequest;
import deoca.hhv.transport.fuelnorm.entity.FuelNorm;
import deoca.hhv.transport.fuelnorm.entity.Purpose;
import deoca.hhv.transport.fuelnorm.repository.FuelNormRepository;
import deoca.hhv.transport.fuelnorm.repository.PurposeRepository;
import deoca.hhv.transport.fuelnorm.service.FuelNormService;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import deoca.hhv.transport.vehicle.enums.VehicleStatus;
import deoca.hhv.transport.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FuelNormServiceImpl implements FuelNormService {

    private final FuelNormRepository fuelNormRepository;
    private final VehicleRepository vehicleRepository;
    private final PurposeRepository purposeRepository;

    @Override
    public FuelNormResponse create(FuelNormRequest request) {

        /*
         * Validate vehicle
         */
        Vehicle vehicle = vehicleRepository
                .findById(request.getVehicleId())
                .orElseThrow(() ->
                        new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        if (
                vehicle.getStatus()
                        ==
                        VehicleStatus.RETIRED
        ) {

            throw new AppException(
                    ErrorCode.VEHICLE_IRETIRED
            );
        }

        /*
         * Validate purpose
         */
        Purpose purpose = purposeRepository
                .findById(request.getPurposeId())
                .orElseThrow(() ->
                        new AppException(ErrorCode.PURPOSE_NOT_FOUND));
        if (
                Boolean.FALSE.equals(
                        purpose.getActive()
                )
        ) {

            throw new AppException(
                    ErrorCode.PURPOSE_INACTIVE
            );
        }

        /*
         * Check duplicate
         */
        boolean exists =
                fuelNormRepository
                        .existsByVehicleIdAndPurposeId(
                                vehicle.getId(),
                                purpose.getId()
                        );

        if (exists) {

            throw new AppException(ErrorCode.FUEL_NORM_ALREADY_EXISTS);
        }

        /*
         * Validate purpose apply fuel norm
         */
        if (Boolean.FALSE.equals(
                purpose.getApplyFuelNorm()
        )) {

            throw new AppException(ErrorCode.PURPOSE_NOT_APPLY_FUEL_NORM);
        }

        /*
         * Validate norm value
         */
        if (request.getNormValue() <= 0) {

            throw new AppException(ErrorCode.INVALID_NORM_VALUE);
        }

        /*
         * Create fuel norm
         */
        FuelNorm fuelNorm = FuelNorm.builder()
                .vehicle(vehicle)
                .purpose(purpose)
                .normValue(request.getNormValue())
                .displayUnit(request.getDisplayUnit())
                .effectiveFrom(request.getEffectiveDate())
                .note(request.getNote())
                .active(true)
                .build();

        fuelNormRepository.save(fuelNorm);

        /*
         * Response
         */
        return mapToResponse(fuelNorm);
    }

    @Override
    public PageResponse<FuelNormResponse> getAll(
            int page,
            int size,
            String vehicleId,
            String purposeId
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("vehicle.licensePlate").ascending());
        Page<FuelNorm> fuelNormPage;

        if (vehicleId != null && !vehicleId.isBlank() && purposeId != null && !purposeId.isBlank()) {
            fuelNormPage = fuelNormRepository.findByVehicleIdAndPurposeId(vehicleId, purposeId, pageable);
        } else if (vehicleId != null && !vehicleId.isBlank()) {
            fuelNormPage = fuelNormRepository.findByVehicleId(vehicleId, pageable);
        } else if (purposeId != null && !purposeId.isBlank()) {
            fuelNormPage = fuelNormRepository.findByPurposeId(purposeId, pageable);
        } else {
            fuelNormPage = fuelNormRepository.findAll(pageable);
        }

        List<FuelNormResponse> content = fuelNormPage.getContent().stream()
                .map(this::mapToResponse)
                .toList();

        return PageResponse.<FuelNormResponse>builder()
                .content(content)
                .page(fuelNormPage.getNumber())
                .size(fuelNormPage.getSize())
                .totalElements(fuelNormPage.getTotalElements())
                .totalPages(fuelNormPage.getTotalPages())
                .last(fuelNormPage.isLast())
                .build();
    }

    @Override
    public FuelNormResponse getById(String id) {
        FuelNorm fuelNorm = fuelNormRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FUEL_NORM_NOT_FOUND));
        return mapToResponse(fuelNorm);
    }

    @Override
    public FuelNormResponse update(String id, FuelNormRequest request) {
        FuelNorm fuelNorm = fuelNormRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FUEL_NORM_NOT_FOUND));

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        Purpose purpose = purposeRepository.findById(request.getPurposeId())
                .orElseThrow(() -> new AppException(ErrorCode.PURPOSE_NOT_FOUND));

        // Kiểm tra xem có đổi sang vehicle/purpose đã có định mức khác chưa
        if (!fuelNorm.getVehicle().getId().equals(vehicle.getId()) ||
                !fuelNorm.getPurpose().getId().equals(purpose.getId())) {
            if (fuelNormRepository.existsByVehicleIdAndPurposeId(vehicle.getId(), purpose.getId())) {
                throw new AppException(ErrorCode.FUEL_NORM_ALREADY_EXISTS);
            }
        }

        fuelNorm.setVehicle(vehicle);
        fuelNorm.setPurpose(purpose);
        fuelNorm.setNormValue(request.getNormValue());
        fuelNorm.setDisplayUnit(request.getDisplayUnit());
        fuelNorm.setEffectiveFrom(request.getEffectiveDate());
        fuelNorm.setNote(request.getNote());
        fuelNorm.setActive(request.getActive() != null ? request.getActive() : fuelNorm.getActive());

        fuelNormRepository.save(fuelNorm);
        return mapToResponse(fuelNorm);
    }

    @Override
    public void delete(String id) {
        if (!fuelNormRepository.existsById(id)) {
            throw new AppException(ErrorCode.FUEL_NORM_ALREADY_EXISTS);
        }
        fuelNormRepository.deleteById(id);
    }

    private FuelNormResponse mapToResponse(FuelNorm fuelNorm) {
        return FuelNormResponse.builder()
                .id(fuelNorm.getId())
                .vehicleId(fuelNorm.getVehicle().getId())
                .vehicleName(fuelNorm.getVehicle().getVehicleType())
                .vehiclePlate(fuelNorm.getVehicle().getLicensePlate())
                .purposeId(fuelNorm.getPurpose().getId())
                .purposeName(fuelNorm.getPurpose().getName())
                .normValue(fuelNorm.getNormValue())
                .displayUnit(fuelNorm.getDisplayUnit())
                .effectiveDate(fuelNorm.getEffectiveFrom())
                .note(fuelNorm.getNote())
                .active(fuelNorm.getActive())
                .build();
    }

}

