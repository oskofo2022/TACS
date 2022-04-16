package controllers.advices;

import domain.errors.APIError;
import domain.errors.runtime.BusinessRuntimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class BusinessRuntimeExceptionControllerAdviser {

    @ExceptionHandler(BusinessRuntimeException.class)
    @ResponseBody
    protected ResponseEntity<APIError> handleConflict(BusinessRuntimeException businessRuntimeException, WebRequest request) {
        var aPIError = new APIError(businessRuntimeException.getCode(), businessRuntimeException.getMessage());
        return new ResponseEntity<>(aPIError, businessRuntimeException.getHttpStatus());
    }

}