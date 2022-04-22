package domain.errors.runtime;

import domain.errors.constants.ErrorCodeConstants;
import org.springframework.http.HttpStatus;

public class UserGetRuntimeException extends BusinessRuntimeException{
    public UserGetRuntimeException(String message) {
        super(message, ErrorCodeConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
