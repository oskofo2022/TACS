package domain.errors.runtime;

import domain.errors.constants.ErrorMessageConstants;

public class FileNotFoundRuntimeException extends RuntimeException {
    public FileNotFoundRuntimeException(String filePath) {
        super(ErrorMessageConstants.getInvalidFilePath(filePath));
    }
}
