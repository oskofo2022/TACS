package domain.persistence.entities.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TournamentStateTest {

    @Test
    void stateReadyCanInscribe() {
        assertTrue(TournamentState.READY::canInscribe);
    }

    @Test
    void stateStartedCanInscribe() {
        assertFalse(TournamentState.STARTED::canInscribe);
    }

    @Test
    void stateEndedCanInscribe() {
        assertFalse(TournamentState.ENDED::canInscribe);
    }
}