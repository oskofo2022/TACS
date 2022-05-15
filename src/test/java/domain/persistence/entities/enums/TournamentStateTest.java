package domain.persistence.entities.enums;

import domain.errors.runtime.TournamentStateInvalidInscriptionRuntimeException;
import domain.persistence.entities.Tournament;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TournamentStateTest {

    @Test
    void stateReadyValidateInscriptionCreation() {
        assertDoesNotThrow(TournamentState.READY::validateInscriptionCreation);
    }

    @Test
    void stateStartedValidateInscriptionCreation() {
        assertThrows(TournamentStateInvalidInscriptionRuntimeException.class, TournamentState.STARTED::validateInscriptionCreation);
    }

    @Test
    void stateEndedValidateInscriptionCreation() {
        assertThrows(TournamentStateInvalidInscriptionRuntimeException.class, TournamentState.ENDED::validateInscriptionCreation);
    }
}