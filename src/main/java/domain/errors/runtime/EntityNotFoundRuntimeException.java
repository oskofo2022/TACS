package domain.errors.runtime;

import domain.errors.constants.ErrorCodeConstants;
import domain.errors.constants.ErrorMessageConstants;
import org.springframework.http.HttpStatus;

public class EntityNotFoundRuntimeException extends BusinessRuntimeException{
    public <T> EntityNotFoundRuntimeException(String entity, Class<T> specificClass) {
        super(ErrorMessageConstants.getEntityNotFound(entity), ErrorCodeConstants.getEntityNotFound(specificClass), HttpStatus.NOT_FOUND);
    }
}
