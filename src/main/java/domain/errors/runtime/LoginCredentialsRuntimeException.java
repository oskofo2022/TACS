package domain.errors.runtime;

import domain.errors.constants.ErrorCodeConstants;
import domain.errors.constants.ErrorMessageConstants;
import org.springframework.http.HttpStatus;

public class LoginCredentialsRuntimeException  extends BusinessRuntimeException {
    public LoginCredentialsRuntimeException() {
        super(ErrorMessageConstants.INVALID_CREDENTIALS, ErrorCodeConstants.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }
}
