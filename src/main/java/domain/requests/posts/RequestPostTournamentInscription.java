package domain.requests.posts;

import java.util.UUID;

public class RequestPostTournamentInscription {
    public UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
