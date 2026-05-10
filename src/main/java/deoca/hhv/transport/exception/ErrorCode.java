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

    //DRIVER
    DRIVER_ALREADY_EXISTS(2001, "Tài xế đã tồn tại",HttpStatus.BAD_REQUEST),
    NATIONAL_ID_EXISTED(2003, "Số điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),
    DRIVER_NOT_FOUND(2004, "Driver not found", HttpStatus.BAD_REQUEST),
    PHONE_ALREADY_EXISTS(2002, "Số điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),

    //PURPOSE
    PURPOSE_CODE_ALREADY_EXISTS(3001, "Mã đã tồn tại", HttpStatus.BAD_REQUEST),
    PURPOSE_NAME_ALREADY_EXISTS(3002, "Tên mục đích đã tồn tại", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private final int code;
    private final String message;
    private final HttpStatus status;
}
