package deoca.hhv.transport.trip.validator;

import deoca.hhv.transport.exception.AppException;
import deoca.hhv.transport.exception.ErrorCode;
import deoca.hhv.transport.trip.entity.TripLog;
import deoca.hhv.transport.trip.enums.TripStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TripLogDetailValidator {

    public void validateTripEditable(
            TripLog tripLog
    ) {

        if(tripLog.getStatus()
                == TripStatus.CLOSED){

            throw new AppException(
                    ErrorCode.TRIP_CLOSED
            );
        }
    }

//    1. Không được nhập ngoài tháng.
    public void validateWorkDate(
            TripLog tripLog,
            LocalDate workDate
    ) {

        if(workDate.getMonthValue()
                != tripLog.getMonth()){

            throw new AppException(
                    ErrorCode.INVALID_WORK_DATE
            );
        }

        if(workDate.getYear()
                != tripLog.getYear()){

            throw new AppException(
                    ErrorCode.INVALID_WORK_DATE
            );
        }
    }

//    2. Bắt buộc có Km cuối và kmEnd > kmStart
    public void validateKm(
            Double startKm,
            Double endKm
    ){

        if(endKm == null){

            throw new AppException(
                    ErrorCode.END_KM_REQUIRED
            );
        }

        if(startKm != null
                && endKm < startKm){

            throw new AppException(
                    ErrorCode.INVALID_KM
            );
        }
    }

//    3. Giờ hoạt động >= 0
    public void validateWorkingHour(
            Double workingHour
    ){

        if(workingHour != null
                && workingHour < 0){

            throw new AppException(
                    ErrorCode.INVALID_WORKING_HOUR
            );
        }
    }

    public void validateIdleHour(
            Double idleHour
    ){

        if(idleHour != null
                && idleHour < 0){

            throw new AppException(
                    ErrorCode.INVALID_IDLE_HOUR
            );
        }
    }
}
