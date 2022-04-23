package domain.persistence.entities;

import domain.persistence.constants.ColumnConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class InscriptionIdentifier implements Serializable {
    @Serial
    private static final long serialVersionUID = 7062436974210439143L;

    private long userId;

    private long tournamentId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InscriptionIdentifier that = (InscriptionIdentifier) o;

        if (userId != that.userId) return false;
        return tournamentId == that.tournamentId;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (tournamentId ^ (tournamentId >>> 32));
        return result;
    }
}
