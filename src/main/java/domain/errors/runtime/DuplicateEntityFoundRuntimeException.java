package domain.errors.runtime;

import domain.errors.constants.ErrorCodeConstants;
import domain.errors.constants.ErrorMessageConstants;
import org.springframework.http.HttpStatus;

public class DuplicateEntityFoundRuntimeException extends BusinessRuntimeException {
    public <T> DuplicateEntityFoundRuntimeException(String entity, Class<T> specificClass) {
        super(ErrorMessageConstants.getDuplicateEntityFound(entity), ErrorCodeConstants.getDuplicateEntityFound(specificClass), HttpStatus.CONFLICT);
    }

    public <T> DuplicateEntityFoundRuntimeException(String duplicateName) {
        super(ErrorMessageConstants.getDuplicateEntityFound(duplicateName), ErrorCodeConstants.getDuplicateEntityFound(duplicateName), HttpStatus.CONFLICT);
    }
}
