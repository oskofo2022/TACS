package domain.persistence.entities.enums;

import domain.errors.runtime.TournamentStateInvalidInscriptionRuntimeException;

public enum TournamentState {
    READY {
        @Override
        public void validateInscriptionCreation() {
        }
    },
    STARTED {
        @Override
        public void validateInscriptionCreation() {
            throw new TournamentStateInvalidInscriptionRuntimeException();
        }
    },
    ENDED {
        @Override
        public void validateInscriptionCreation() {
            throw new TournamentStateInvalidInscriptionRuntimeException();
        }
    };

    abstract public void validateInscriptionCreation();
}
