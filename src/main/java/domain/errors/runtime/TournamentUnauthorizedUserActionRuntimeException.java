package domain.errors.runtime;

import domain.errors.constants.ErrorCodeConstants;
import domain.errors.constants.ErrorMessageConstants;
import org.springframework.http.HttpStatus;

public class TournamentUnauthorizedUserActionRuntimeException extends BusinessRuntimeException {
    public TournamentUnauthorizedUserActionRuntimeException() {
        super(ErrorMessageConstants.TOURNAMENT_UNAUTHORIZED_USER_ACTION, ErrorCodeConstants.TOURNAMENT_UNAUTHORIZED_USER_ACTION, HttpStatus.UNAUTHORIZED);
    }
}
