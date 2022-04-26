package domain.errors.runtime;

import domain.errors.constants.ErrorCodeConstants;
import domain.errors.constants.ErrorMessageConstants;
import org.springframework.http.HttpStatus;

public class TournamentStateInvalidInscriptionRuntimeException extends BusinessRuntimeException {
    public TournamentStateInvalidInscriptionRuntimeException() {
        super(ErrorMessageConstants.INVALID_TOURNAMENT_INSCRIPTION, ErrorCodeConstants.INVALID_TOURNAMENT_INSCRIPTION, HttpStatus.BAD_REQUEST);
    }
}
