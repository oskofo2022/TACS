package domain.persistence.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MatchIdentifier implements Serializable {
    private long userId;

    private long tournamentId;

    private long gameId;

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

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchIdentifier that = (MatchIdentifier) o;

        if (userId != that.userId) return false;
        if (tournamentId != that.tournamentId) return false;
        return gameId == that.gameId;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (tournamentId ^ (tournamentId >>> 32));
        result = 31 * result + (int) (gameId ^ (gameId >>> 32));
        return result;
    }
}
