package domain.errors.runtime;

import domain.errors.constants.ErrorCodeConstants;
import org.springframework.http.HttpStatus;

public class EntityNotFoundRuntimeException extends BusinessRuntimeException{
    public <T> EntityNotFoundRuntimeException(String message, Class<T> specificClass) {
        super(message, ErrorCodeConstants.getEntityNotFound(specificClass), HttpStatus.NOT_FOUND);
    }
}
