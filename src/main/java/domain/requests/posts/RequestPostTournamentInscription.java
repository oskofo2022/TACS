package domain.requests.posts;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class RequestPostTournamentInscription {
    @NotNull
    public UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
