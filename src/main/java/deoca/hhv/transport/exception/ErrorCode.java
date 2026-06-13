package deoca.hhv.transport.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    //VEHICLE
    VEHICLE_EXISTED(1001, "Phương tiện đã tồn tại", HttpStatus.BAD_REQUEST),
    VEHICLE_NOT_FOUND(1002, "Không tìm thấy phương tiện", HttpStatus.NOT_FOUND),

    INVALID_KEY(1003, "Invalid message key", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR(1004, "Validation failed", HttpStatus.BAD_REQUEST),
    VEHICLE_IN_USE(
            1005,
            "Phương tiện đang được sử dụng",
            HttpStatus.BAD_REQUEST
    ),

    VEHICLE_IRETIRED(
            1006,
            "Phương tiện đã thanh lý",
            HttpStatus.BAD_REQUEST
    ),

    //DRIVER
    DRIVER_ALREADY_EXISTS(2001, "Tài xế đã tồn tại", HttpStatus.BAD_REQUEST),
    NATIONAL_ID_EXISTED(2003, "Số điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),
    DRIVER_NOT_FOUND(2004, "Driver not found", HttpStatus.BAD_REQUEST),
    PHONE_ALREADY_EXISTS(2002, "Số điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),

    //PURPOSE
    PURPOSE_CODE_ALREADY_EXISTS(3001, "Mã đã tồn tại", HttpStatus.BAD_REQUEST),
    PURPOSE_NAME_ALREADY_EXISTS(3002, "Tên mục đích đã tồn tại", HttpStatus.BAD_REQUEST),
    PURPOSE_NOT_FOUND(3003, "Không tìm thấy mục đích", HttpStatus.BAD_REQUEST),
    PURPOSE_NOT_APPLY_FUEL_NORM(3004,
            "Mục đích này không áp dụng định mức nhiên liệu",
            HttpStatus.BAD_REQUEST),
    PURPOSE_INACTIVE(
            3005,
            "Mục đích đã ngừng sử dụng",
            HttpStatus.BAD_REQUEST
    ),


    PURPOSE_ALREADY_INACTIVE(
            3005,
            "Mục đích đã ngừng sử dụng",
            HttpStatus.BAD_REQUEST
    ),

    //FUEL NORM
    FUEL_NORM_ALREADY_EXISTS(4001, "Định mức cho phương tiện và mục đích này đã tồn tại", HttpStatus.BAD_REQUEST),
    FUEL_NORM_NOT_FOUND(4002,
            "Không tìm thấy định mức nhiên liệu",
            HttpStatus.NOT_FOUND),

    // PURPOSE & FUEL NORM VALIDATION
    INVALID_NORM_VALUE(
            5001,
            "Giá trị định mức phải lớn hơn 0",
            HttpStatus.BAD_REQUEST),

    FUEL_NORM_ALREADY_INACTIVE(
            5003,
            "Định mức đã ngừng áp dụng",
            HttpStatus.BAD_REQUEST
    ),

    // TRIP LOG


    // ==========================
    // Trip
    // ==========================

    TRIP_NOT_FOUND(
            2001,
                    "Không tìm thấy nhật trình",
            HttpStatus.BAD_REQUEST
    ),

    TRIP_CLOSED(
            2017,
            "Nhật trình đã chốt",
            HttpStatus.BAD_REQUEST
    ),


    INVALID_WORK_DATE(
            2003,
                    "Ngày làm việc không thuộc tháng nhật trình",
            HttpStatus.BAD_REQUEST
    ),

    END_KM_REQUIRED(
            2004,
                    "Bắt buộc nhập km cuối",
            HttpStatus.BAD_REQUEST
    ),

    INVALID_KM(
            2005,
                    "Km cuối phải lớn hơn hoặc bằng km đầu",
            HttpStatus.BAD_REQUEST
    ),

    INVALID_WORKING_HOUR(
            2006,
                    "Giờ hoạt động không hợp lệ",
            HttpStatus.BAD_REQUEST
    ),

    INVALID_IDLE_HOUR(
            2007,
                    "Giờ nổ máy không hợp lệ",
            HttpStatus.BAD_REQUEST
    ),

    DRIVER_NOT_ACTIVE(
            2008,
                    "Tài xế đang ngừng hoạt động",
            HttpStatus.BAD_REQUEST
    ),

    INVALID_TRIP_STATUS(
            2009,
                    "Trạng thái nhật trình không hợp lệ",
            HttpStatus.BAD_REQUEST
    ),

    DUPLICATE_TRIP(
            2010,
                    "Nhật trình tháng đã tồn tại",
            HttpStatus.BAD_REQUEST
    ),


    TRIP_ALREADY_CLOSED(
            2011,
            "Nhật trình đã được chốt",
            HttpStatus.BAD_REQUEST
    ),

    TRIP_DETAIL_EMPTY(
            2012,
            "Nhật trình chưa có dữ liệu",
            HttpStatus.BAD_REQUEST
    ),

    OPENING_KM_REQUIRED(
            2014,
            "Thiếu km đầu tháng",
            HttpStatus.BAD_REQUEST
    ),

    CLOSING_KM_REQUIRED(
            2015,
            "Thiếu km cuối tháng",
            HttpStatus.BAD_REQUEST
    ),

    NO_FUEL_RECEIVED(
            2016,
            "Số lượng nhiêu liệu nạp >=0.",
            HttpStatus.BAD_REQUEST
    ),

    TRIP_ALREADY_OPEN(
            2019,
            "Nhật trình đang mở",
            HttpStatus.BAD_REQUEST
    ),

    //TRIPLOGDETAIL
    TRIP_DETAIL_NOT_FOUND(
            3001,
            "Dòng nhật trình không tìm thấy",
            HttpStatus.BAD_REQUEST
    ),

    DUPLICATE_WORK_DATE(
            3002,
            "Trùng ngày",
            HttpStatus.BAD_REQUEST
    ),


    AUTO_GENERATED_DETAIL_CANNOT_DELETE(
            3003,
            "Không thể xóa dòng nhật trình",
            HttpStatus.BAD_REQUEST
    )
    ;




    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private final int code;
    private final String message;
    private final HttpStatus status;
}
