package deoca.hhv.transport.trip.service.impl;

import deoca.hhv.transport.driver.entity.Driver;
import deoca.hhv.transport.driver.repository.DriverRepository;
import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.trip.dto.request.TripCreateRequest;
import deoca.hhv.transport.trip.dto.request.TripSearchRequest;
import deoca.hhv.transport.trip.dto.response.TripLogDetailResponse;
import deoca.hhv.transport.trip.dto.response.TripResponse;
import deoca.hhv.transport.trip.dto.response.TripSummaryResponse;
import deoca.hhv.transport.trip.entity.TripLog;
import deoca.hhv.transport.trip.entity.TripLogDetail;
import deoca.hhv.transport.trip.enums.TripStatus;
import deoca.hhv.transport.trip.repository.TripLogDetailRepository;
import deoca.hhv.transport.trip.repository.TripLogRepository;
import deoca.hhv.transport.trip.service.TripLogService;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import deoca.hhv.transport.vehicle.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripLogServiceImpl
        implements TripLogService {

    private final TripLogRepository tripLogRepository;

    private final DriverRepository driverRepository;

    private final VehicleRepository vehicleRepository;

    private final ModelMapper mapper;

    private final TripLogDetailRepository tripLogDetailRepository;

    @Override
    public void addFuelIssueToTrip(
            FuelIssue fuelIssue
    ) {
        LocalDate issueDate =
                LocalDate.from(fuelIssue.getFuelTime());

        int month =
                issueDate.getMonthValue();

        int year =
                issueDate.getYear();

        Vehicle vehicle =
                fuelIssue.getVehicle();

        // tìm nhật trình tháng

        TripLog tripLog =
                tripLogRepository
                        .findByVehicleIdAndMonthAndYear(
                                vehicle.getId(),
                                month,
                                year
                        )
                        .orElseGet(
                                () ->
                                        createMonthlyTrip(
                                                vehicle,
                                                month,
                                                year
                                        )
                        );

        TripLogDetail detail =
                new TripLogDetail();

        detail.setTripLog(
                tripLog
        );

        detail.setFuelIssue(fuelIssue);

        detail.setWorkDate(
                issueDate
        );

        detail.setFuelReceived(
                fuelIssue.getQuantity()
        );

        detail.setNote(
                "Tự động từ phiếu cấp phát"
        );

        tripLogDetailRepository.save(
                detail
        );
    }


    /**
     * Tạo nhật trình tháng mới
     */
    private TripLog createMonthlyTrip(
            Vehicle vehicle,
            Integer month,
            Integer year
    ) {

        TripLog tripLog = new TripLog();

        tripLog.setVehicle(vehicle);

        tripLog.setMonth(month);

        tripLog.setYear(year);

        return tripLogRepository.save(tripLog);
    }

    @Override
    @Transactional(readOnly = true)
    public TripResponse getById(
            String tripId
    ) {
        TripLog tripLog =
                tripLogRepository.findById(
                                tripId
                        )
                        .orElseThrow(
                                () -> new AppException(
                                        ErrorCode.TRIP_NOT_FOUND
                                )
                        );

        List<TripLogDetailResponse> details =
                tripLogDetailRepository
                        .findByTripLogIdOrderByWorkDateAsc(
                                tripId
                        )
                        .stream()
                        .map(detail ->
                                mapper.map(
                                        detail,
                                        TripLogDetailResponse.class
                                )
                        )
                        .toList();

        return TripResponse.builder()
//                .id(tripLog.getTripCode())
                .vehicleId(
                        tripLog.getVehicle().getId()
                )
                .licensePlate(
                        tripLog.getVehicle()
                                .getLicensePlate()
                )
                .driverId(
                        tripLog.getDriver().getId()
                )
                .driverName(
                        tripLog.getDriver().getFullName()
                )
                .month(
                        tripLog.getMonth()
                )
                .year(
                        tripLog.getYear()
                )
                .details(
                        details
                )
                .build();
    }

    @Override
    @Transactional
    public TripResponse createTrip(
            TripCreateRequest request
    ) {
        Vehicle vehicle =
                vehicleRepository.findById(
                                request.getVehicleId()
                        )
                        .orElseThrow(
                                () -> new AppException(ErrorCode.VEHICLE_NOT_FOUND)
                        );

        Driver driver =
                driverRepository.findById(
                                request.getDriverId()
                        )
                        .orElseThrow(
                                () -> new AppException(ErrorCode.DRIVER_NOT_FOUND)
                        );

        boolean existed =
                tripLogRepository
                        .existsByVehicle_IdAndMonthAndYear(
                                vehicle.getId(),
                                request.getMonth(),
                                request.getYear()
                        );

        if (existed) {

            throw new RuntimeException(
                    "Nhật trình tháng đã tồn tại"
            );
        }

        TripLog tripLog = new TripLog();

        tripLog.getId();

        tripLog.setVehicle(vehicle);

        tripLog.setDriver(driver);

        tripLog.setMonth(
                request.getMonth()
        );

        tripLog.setYear(
                request.getYear()
        );

//        tripLog.setTripDate(
//                LocalDate.of(
//                        request.getYear(),
//                        request.getMonth(),
//                        1
//                )
//        );

        tripLog.setTripCode(
                generateTripCode(
                        vehicle,
                        request.getMonth(),
                        request.getYear()
                )
        );

        tripLog.setTotalKm(0D);

        tripLog.setTotalWorkingHour(0D);

        tripLog.setTotalIdleHour(0D);

        tripLog.setTotalFuelReceived(0D);

        tripLog.setStatus(
                TripStatus.OPEN
        );

        tripLog = tripLogRepository.save(
                tripLog
        );

        return TripResponse.builder()
                .vehicleId(vehicle.getId())
                .licensePlate(vehicle.getLicensePlate())
                .driverId(driver.getId())
                .driverName(driver.getFullName())
                .month(tripLog.getMonth())
                .year(tripLog.getYear())
                .totalKm(0D)
                .totalWorkingHour(0D)
                .totalIdleHour(0D)
                .totalFuelReceived(0D)
                .status(tripLog.getStatus().name())
                .tripCode(
                        tripLog.getTripCode()
                )
                .build();
    }

//    Filter auto
    @Override
    @Transactional(readOnly = true)
    public List<TripSummaryResponse> searchTrips(
            TripSearchRequest request
    ) {
        Specification<TripLog> spec =
                (root, query, cb) -> cb.conjunction();

        if (request.getVehicleId() != null) {

            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("vehicle").get("id"),
                                    request.getVehicleId()
                            )
            );
        }

        if (request.getMonth() != null) {

            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("month"),
                                    request.getMonth()
                            )
            );
        }

        if (request.getYear() != null) {

            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("year"),
                                    request.getYear()
                            )
            );
        }

        if (request.getClosed() != null) {

            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("closed"),
                                    request.getClosed()
                            )
            );
        }

        if (request.getDriverId() != null) {

            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("driver").get("id"),
                                    request.getDriverId()
                            )
            );
        }

        return tripLogRepository
                .findAll(spec)
                .stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    private String generateTripCode(
            Vehicle vehicle,
            Integer month,
            Integer year
    ) {

        return "TRIP-"
                + vehicle.getLicensePlate()
                + "-"
                + String.format("%02d", month)
                + "-"
                + year;
    }

    private TripSummaryResponse toSummaryResponse(
            TripLog trip
    ) {

        return TripSummaryResponse.builder()

                .id(trip.getId())

                .tripCode(trip.getTripCode())

                .vehicleId(
                        trip.getVehicle().getId()
                )

                .licensePlate(
                        trip.getVehicle().getLicensePlate()
                )

                .driverId(
                        trip.getDriver().getId()
                )

                .driverName(
                        trip.getDriver().getFullName()
                )

                .month(trip.getMonth())

                .year(trip.getYear())

                .totalKm(trip.getTotalKm())

                .totalFuelReceived(
                        trip.getTotalFuelReceived()
                )

                .actualFuelConsumption(
                        trip.getActualFuelConsumption()
                )

                .standardFuelConsumption(
                        trip.getStandardFuelConsumption()
                )

                .exceedPercent(
                        trip.getExceedPercent()
                )

                .warningLevel(
                        trip.getWarningLevel() == null
                                ? null
                                : trip.getWarningLevel().name()
                )

                .status(
                        trip.getStatus() == null
                                ? null
                                : trip.getStatus().name()
                )

                .build();
    }
}
