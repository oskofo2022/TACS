package controllers.advices;

import domain.errors.APIError;
import domain.errors.constants.ErrorCodeConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdviser extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected ResponseEntity<APIError> handleConflict(Exception exception, WebRequest request) {
        var aPIError = new APIError(ErrorCodeConstants.UNHANDLED_EXCEPTION, "Internal error");
        return new ResponseEntity<>(aPIError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}