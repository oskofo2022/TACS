package domain.persistence.entities.enums;

import domain.errors.runtime.TournamentStateInvalidInscriptionRuntimeException;
import domain.persistence.entities.Tournament;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

public enum TournamentState {
    READY {
        @Override
        public boolean canInscribe() {
            return true;
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
        public boolean canInscribe() {
            return false;
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
        public boolean canInscribe() {
            return false;
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

    abstract public boolean canInscribe();

    public abstract boolean hasStarted();

    public abstract TournamentState getState(Tournament tournament);
}
