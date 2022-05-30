package domain.persistence.entities.enums;

import domain.errors.runtime.TournamentStateInvalidInscriptionRuntimeException;
import domain.persistence.entities.Tournament;

import java.time.LocalDate;

public enum TournamentState {
    READY {
        @Override
        public void validateInscriptionCreation() {
        }

        @Override
        public boolean hasStarted() {
            return false;
        }

        @Override
        public TournamentState getState(Tournament tournament) {
            if (LocalDate.now().isBefore(tournament.getStartDate())) {
                return this;
            }

            return STARTED.getState(tournament);
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

        @Override
        public TournamentState getState(Tournament tournament) {
            if (LocalDate.now().isBefore(tournament.getEndDate().plusDays(1))) {
                return this;
            }

            return ENDED.getState(tournament);
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

        @Override
        public TournamentState getState(Tournament tournament) {
            return this;
        }
    };

    abstract public void validateInscriptionCreation();

    public abstract boolean hasStarted();

    public abstract TournamentState getState(Tournament tournament);
}
