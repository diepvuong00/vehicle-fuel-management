package deoca.hhv.transport.trip.service.impl;

import deoca.hhv.transport.driver.entity.Driver;
import deoca.hhv.transport.driver.repository.DriverRepository;
import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.fuelnorm.entity.FuelNorm;
import deoca.hhv.transport.fuelnorm.enums.FuelWarningLevel;
import deoca.hhv.transport.fuelnorm.repository.FuelNormRepository;
import deoca.hhv.transport.trip.calculator.FuelNormCalculator;
import deoca.hhv.transport.trip.dto.request.TripCreateRequest;
import deoca.hhv.transport.trip.dto.request.TripSearchRequest;
import deoca.hhv.transport.trip.dto.response.*;
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
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripLogServiceImpl
        implements TripLogService {

    private final TripLogRepository tripLogRepository;


    private final FuelNormRepository fuelNormRepository;

    private final FuelNormCalculator fuelNormCalculator;

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
                                                fuelIssue.getDriver(),
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

        detail.setDriver(fuelIssue.getDriver());

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
            Driver driver,
            Integer month,
            Integer year
    ) {

        TripLog tripLog = new TripLog();

        tripLog.setVehicle(vehicle);
        tripLog.setDriver(driver);

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
                        tripLog.getDriver() != null ? tripLog.getDriver().getId() : null
                )
                .driverName(
                        tripLog.getDriver() != null ? tripLog.getDriver().getFullName() : null
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

    //    Xem chi tiết nhật trình
    @Override
    @Transactional(readOnly = true)
    public TripDetailResponse getTripDetail(
            String tripId
    ) {
        TripLog tripLog =
                tripLogRepository
                        .findById(tripId)
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
                        .map(this::mapDetailResponse)
                        .toList();

        return TripDetailResponse.builder()

                .tripId(
                        tripLog.getId()
                )

                .tripCode(
                        tripLog.getTripCode()
                )

                .month(
                        tripLog.getMonth()
                )

                .year(
                        tripLog.getYear()
                )

                .vehicleId(
                        tripLog.getVehicle().getId()
                )

                .licensePlate(
                        tripLog.getVehicle().getLicensePlate()
                )

                .driverId(
                        tripLog.getDriver() != null ? tripLog.getDriver().getId() : null
                )

                .driverName(
                        tripLog.getDriver() != null ? tripLog.getDriver().getFullName() : null
                )

                .openingKm(
                        tripLog.getOpeningKm()
                )

                .closingKm(
                        tripLog.getClosingKm()
                )

                .totalKm(
                        tripLog.getTotalKm()
                )

                .totalWorkingHour(
                        tripLog.getTotalWorkingHour()
                )

                .totalIdleHour(
                        tripLog.getTotalIdleHour()
                )

                .totalFuelReceived(
                        tripLog.getTotalFuelReceived()
                )

                .standardFuelConsumption(
                        tripLog.getStandardFuelConsumption()
                )

                .actualFuelConsumption(
                        tripLog.getActualFuelConsumption()
                )

                .exceedPercent(
                        tripLog.getExceedPercent()
                )

                .warningLevel(
                        tripLog.getWarningLevel() == null
                                ? null
                                : tripLog.getWarningLevel().name()
                )

                .status(
                        tripLog.getStatus() == null
                                ? null
                                : tripLog.getStatus().name()
                )

                .details(
                        details
                )

                .build();
    }


//    Chốt nhật trình tháng

    @Override
    @Transactional
    public TripCloseResponse closeTrip(
            String tripId
    ) {
//        1.Tìm nhật trình
        TripLog tripLog =
                tripLogRepository
                        .findById(tripId)
                        .orElseThrow(
                                () ->
                                        new AppException(
                                                ErrorCode.TRIP_NOT_FOUND
                                        )
                        );

//        2.Kiểm tra nhật trình đã đóng chưa
        if (
                tripLog.getStatus()
                        ==
                        TripStatus.CLOSED
        ) {

            throw new AppException(
                    ErrorCode.TRIP_ALREADY_CLOSED
            );
        }


//        3. Lấy toàn bộ nhật trình
        List<TripLogDetail> details =
                tripLogDetailRepository
                        .findByTripLogIdOrderByWorkDateAsc(
                                tripId
                        );

//        4. Kiểm tra dữ liệu
        if (details.isEmpty()) {

            throw new AppException(
                    ErrorCode.TRIP_DETAIL_EMPTY
            );
        }

//        5. Lấy km đầu

        TripLogDetail firstDetail =
                details.stream()
                        .filter(
                                d -> d.getStartKm() != null
                        )
                        .min(
                                Comparator.comparing(
                                        TripLogDetail::getWorkDate
                                )
                        )
                        .orElseThrow(
                                () -> new AppException(
                                        ErrorCode.OPENING_KM_REQUIRED
                                )
                        );

//        6. Lấy km cuối
        TripLogDetail lastDetail =
                details.stream()
                        .filter(
                                d -> d.getEndKm() != null
                        )
                        .max(
                                Comparator.comparing(
                                        TripLogDetail::getWorkDate
                                )
                        )
                        .orElseThrow(
                                () -> new AppException(
                                        ErrorCode.CLOSING_KM_REQUIRED
                                )
                        );

//        7. Tính tổng km
        Double totalKm =
                details.stream()
                        .mapToDouble(
                                d ->
                                        d.getDistance() == null
                                                ? 0D
                                                : d.getDistance()
                        )
                        .sum();

//        8. Tính tổng giờ hoạt động
        Double totalWorkingHour =
                details.stream()
                        .mapToDouble(
                                d ->
                                        d.getWorkingHour() == null
                                                ? 0D
                                                : d.getWorkingHour()
                        )
                        .sum();

//        9. Tính tổng h nổ may
        Double totalIdleHour =
                details.stream()
                        .mapToDouble(
                                d ->
                                        d.getIdleHour() == null
                                                ? 0D
                                                : d.getIdleHour()
                        )
                        .sum();

//        10. Tính tongr nhiên liệu tiếp nhận
        Double totalFuelReceived =
                details.stream()
                        .mapToDouble(
                                d ->
                                        d.getFuelReceived() == null
                                                ? 0D
                                                : d.getFuelReceived()
                        )
                        .sum();

//        11. Cập nhật triplog
        tripLog.setOpeningKm(
                firstDetail.getStartKm()
        );

        tripLog.setClosingKm(
                lastDetail.getEndKm()
        );

        tripLog.setTotalKm(
                totalKm
        );

        tripLog.setTotalWorkingHour(
                totalWorkingHour
        );

        tripLog.setTotalIdleHour(
                totalIdleHour
        );

        tripLog.setTotalFuelReceived(
                totalFuelReceived
        );

//        12. Tính định mức xe
//        12.1 Lấy định mức của xe
        List<FuelNorm> fuelNorms =
                fuelNormRepository
                        .findByVehicleIdAndActiveTrue(
                                tripLog
                                        .getVehicle()
                                        .getId()
                        );
//        12.2 Tính định mức thực tế
        Double standardFuel =
                fuelNormCalculator
                        .calculate(
                                totalKm,
                                totalWorkingHour,
                                totalIdleHour,
                                fuelNorms
                        );

        tripLog.setStandardFuelConsumption(
                standardFuel
        );


//        Đã cấp bao nhiêu dầu
//                =
//                Tiêu hao thực tế
        tripLog.setActualFuelConsumption(
                totalFuelReceived
        );

        if (totalFuelReceived <= 0) {
            throw new AppException(
                    ErrorCode.NO_FUEL_RECEIVED
            );
        }

//        13. Tính %
        double exceedPercent = 0D;
        if (
                standardFuel != null
                        &&
                        standardFuel > 0
        ) {

            exceedPercent =
                    (
                            totalFuelReceived
                                    -
                                    standardFuel
                    )
                            /
                            standardFuel
                            *
                            100;
        }

        tripLog.setExceedPercent(
                exceedPercent
        );

        tripLog.setStatus(
                TripStatus.CLOSED
        );

        tripLogRepository.save(
                tripLog
        );


        FuelWarningLevel warningLevel =
                determineWarningLevel(
                        exceedPercent
                );

        tripLog.setWarningLevel(
                warningLevel
        );

        tripLog.setStatus(
                TripStatus.CLOSED
        );

        tripLogRepository.save(
                tripLog
        );

        return TripCloseResponse.builder()
                .tripId(tripLog.getId())
                .tripCode(tripLog.getTripCode())
                .totalKm(tripLog.getTotalKm())
                .totalWorkingHour(tripLog.getTotalWorkingHour())
                .totalIdleHour(tripLog.getTotalIdleHour())
                .totalFuelReceived(tripLog.getTotalFuelReceived())
                .standardFuelConsumption(
                        tripLog.getStandardFuelConsumption()
                )
                .actualFuelConsumption(
                        tripLog.getActualFuelConsumption()
                )
                .exceedPercent(
                        tripLog.getExceedPercent()
                )
                .warningLevel(
                        tripLog.getWarningLevel().name()
                )
                .status(
                        tripLog.getStatus().name()
                )
                .build();
    }

    @Override
    @Transactional
    public TripReopenResponse reopenTrip(
            String tripId
    ) {
        TripLog tripLog =
                tripLogRepository
                        .findById(tripId)
                        .orElseThrow(
                                () ->
                                        new AppException(
                                                ErrorCode.TRIP_NOT_FOUND
                                        )
                        );

        if (
                tripLog.getStatus()
                        ==
                        TripStatus.OPEN
        ) {

            throw new AppException(
                    ErrorCode.TRIP_ALREADY_OPEN
            );
        }

        tripLog.setStatus(
                TripStatus.OPEN
        );

        tripLogRepository.save(
                tripLog
        );

        return TripReopenResponse
                .builder()
                .tripId(
                        tripLog.getId()
                )
                .tripCode(
                        tripLog.getTripCode()
                )
                .status(
                        tripLog.getStatus().name()
                )
                .message(
                        "Mở lại nhật trình thành công"
                )
                .build();
    }

    private FuelWarningLevel determineWarningLevel(
            double exceedPercent
    ) {

        if (exceedPercent >= 15) {
            return FuelWarningLevel.DANGEROUS;
        }

        if (exceedPercent >= 10) {
            return FuelWarningLevel.WARNING;
        }

        return FuelWarningLevel.NORMAL;


    }

//

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
                        trip.getDriver() != null ? trip.getDriver().getId() : null
                )

                .driverName(
                        trip.getDriver() != null ? trip.getDriver().getFullName() : null
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


    private TripLogDetailResponse mapDetailResponse(
            TripLogDetail detail
    ) {

        return TripLogDetailResponse.builder()

                .id(
                        detail.getId()
                )

                .tripLogId(
                        detail.getTripLog().getId()
                )

                .driverId(
                        detail.getDriver() != null ? detail.getDriver().getId() : null
                )

                .driverName(
                        detail.getDriver() != null ? detail.getDriver().getFullName() : null
                )

                .workDate(
                        detail.getWorkDate()
                )

                .workContent(
                        detail.getWorkContent()
                )

                .startKm(
                        detail.getStartKm()
                )

                .endKm(
                        detail.getEndKm()
                )

                .distance(
                        detail.getDistance()
                )

                .workingHour(
                        detail.getWorkingHour()
                )

                .idleHour(
                        detail.getIdleHour()
                )

                .fuelReceived(
                        detail.getFuelReceived()
                )

                .note(
                        detail.getNote()
                )

                .autoGenerated(
                        detail.getAutoGenerated()
                )

                .build();
    }


}
