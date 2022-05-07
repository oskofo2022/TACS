package domain.errors.runtime;

import domain.errors.constants.ErrorCodeConstants;
import domain.errors.constants.ErrorMessageConstants;
import org.springframework.http.HttpStatus;

public class DuplicateEntityFoundRuntimeException extends BusinessRuntimeException {
    public <T> DuplicateEntityFoundRuntimeException(Class<T> specificClass) {
        super(ErrorMessageConstants.getDuplicateEntityFound(specificClass), ErrorCodeConstants.getDuplicateEntityFound(specificClass), HttpStatus.CONFLICT);
    }
}
