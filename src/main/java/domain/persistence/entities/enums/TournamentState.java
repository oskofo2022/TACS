package domain.persistence.entities.enums;

import domain.errors.runtime.TournamentStateInvalidInscriptionRuntimeException;

public enum TournamentState {
    READY {
        @Override
        public void validateInscriptionCreation() {
        }

        @Override
        public boolean hasStarted() {
            return false;
        }
    },
    STARTED {
        @Override
        public void validateInscriptionCreation() {
            throw new TournamentStateInvalidInscriptionRuntimeException();
        }

        @Override
        public boolean hasStarted() {
            return true;
        }
    },
    ENDED {
        @Override
        public void validateInscriptionCreation() {
            throw new TournamentStateInvalidInscriptionRuntimeException();
        }

        @Override
        public boolean hasStarted() {
            return true;
        }
    };

    abstract public void validateInscriptionCreation();

    public abstract boolean hasStarted();
}
