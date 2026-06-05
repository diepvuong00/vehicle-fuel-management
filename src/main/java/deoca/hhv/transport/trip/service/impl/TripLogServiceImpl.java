package deoca.hhv.transport.trip.service.impl;

import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.trip.dto.response.TripLogDetailResponse;
import deoca.hhv.transport.trip.dto.response.TripResponse;
import deoca.hhv.transport.trip.entity.TripLog;
import deoca.hhv.transport.trip.entity.TripLogDetail;
import deoca.hhv.transport.trip.repository.TripLogDetailRepository;
import deoca.hhv.transport.trip.repository.TripLogRepository;
import deoca.hhv.transport.trip.service.TripLogService;
import deoca.hhv.transport.vehicle.entity.Vehicle;
import org.modelmapper.ModelMapper;
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

        tripLog.setClosed(false);

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
                .id(tripLog.getId())
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
                .closed(
                        tripLog.getClosed()
                )
                .details(
                        details
                )
                .build();
    }
}
