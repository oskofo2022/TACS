package constants.advices;

import domain.errors.APIError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class BadRequestControllerAdviser extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<APIError> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {
        var fieldError = methodArgumentNotValidException.getFieldError();
        var aPIError = new APIError("CODE", fieldError.getDefaultMessage());
        return new ResponseEntity<>(aPIError, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
