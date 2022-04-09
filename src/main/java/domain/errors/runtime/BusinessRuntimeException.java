package domain.errors.runtime;

import org.springframework.http.HttpStatus;

public class BusinessRuntimeException extends RuntimeException {
    private final String code;
    private final HttpStatus httpStatus;

    public BusinessRuntimeException(String message, String code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
