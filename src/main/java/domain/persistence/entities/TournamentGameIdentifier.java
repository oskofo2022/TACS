package domain.persistence.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TournamentGameIdentifier implements Serializable{

    private long gameId;
    private long tournamentId;

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
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

        TournamentGameIdentifier that = (TournamentGameIdentifier) o;

        if (gameId != that.gameId) return false;
        return tournamentId == that.tournamentId;
    }

    @Override
    public int hashCode() {
        int result = (int) (gameId ^ (gameId >>> 32));
        result = 31 * result + (int) (tournamentId ^ (tournamentId >>> 32));
        return result;
    }
}