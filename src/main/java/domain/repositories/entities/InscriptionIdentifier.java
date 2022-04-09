package domain.repositories.entities;

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
}
