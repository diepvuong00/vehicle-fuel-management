package deoca.hhv.transport.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    VEHICLE_EXISTED(1001, "Vehicle already exists", HttpStatus.BAD_REQUEST),
    VEHICLE_NOT_FOUND(1002, "Vehicle not found", HttpStatus.NOT_FOUND),

    INVALID_KEY(1003, "Invalid message key", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR(1004, "Validation failed", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private final int code;
    private final String message;
    private final HttpStatus status;
}
