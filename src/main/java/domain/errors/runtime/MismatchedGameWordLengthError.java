package domain.errors.runtime;

import domain.errors.ErrorCodeConstants;
import org.springframework.http.HttpStatus;

public class MismatchedGameWordLengthError extends BusinessRuntimeException {
    public MismatchedGameWordLengthError(String message) {
        super(message, ErrorCodeConstants.MISMATCHED_WORD_GAME_LENGTH, HttpStatus.BAD_REQUEST);
    }
}
