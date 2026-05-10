package deoca.hhv.transport.trip.service.impl;

import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import deoca.hhv.transport.trip.dto.reponse.FuelNormResponse;
import deoca.hhv.transport.trip.dto.request.FuelNormRequest;
import deoca.hhv.transport.trip.entity.FuelNorm;
import deoca.hhv.transport.trip.entity.Purpose;
import deoca.hhv.transport.trip.repository.FuelNormRepository;
import deoca.hhv.transport.trip.repository.PurposeRepository;
import deoca.hhv.transport.trip.service.FuelNormService;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import deoca.hhv.transport.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        /*
         * Validate purpose
         */
        Purpose purpose = purposeRepository
                .findById(request.getPurposeId())
                .orElseThrow(() ->
                        new AppException(ErrorCode.PURPOSE_NOT_FOUND));

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

            throw new AppException(ErrorCode.PUEL_NORM_ALREADY_EXISTS);
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
        return FuelNormResponse.builder()
                .id(fuelNorm.getId())

                .vehicleId(vehicle.getId())
                .vehicleName(vehicle.getVehicleType())
                .vehiclePlate(vehicle.getLicensePlate())

                .purposeId(purpose.getId())
                .purposeName(purpose.getName())

                .normValue(fuelNorm.getNormValue())
                .displayUnit(fuelNorm.getDisplayUnit())
                .effectiveDate(fuelNorm.getEffectiveFrom())
                .note(fuelNorm.getNote())
                .active(fuelNorm.getActive())
                .build();
    }

}
