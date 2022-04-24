package controllers.advices;

import domain.errors.APIError;
import domain.errors.constants.ErrorCodeConstants;
import domain.errors.constants.ErrorMessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BadCredentialsExceptionControllerAdviser extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    protected ResponseEntity<APIError> handleConflict(Exception exception, WebRequest request) {
        var aPIError = new APIError(ErrorCodeConstants.INVALID_CREDENTIALS, ErrorMessageConstants.INVALID_CREDENTIALS);
        return new ResponseEntity<>(aPIError, HttpStatus.BAD_REQUEST);
    }
}
