package domain.requests.posts;

import domain.requests.RequestAnnotationTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class RequestPostTournamentInscriptionTest extends RequestAnnotationTest<RequestPostTournamentInscription> {

    @Test
    public void userIdNotSet() {
        this.request.setUserId(null);

        super.invalid("userId", "NotNull");
    }

    @Override
    protected RequestPostTournamentInscription createValidRequest() {
        final var requestPostTournamentInscription = new RequestPostTournamentInscription();
        requestPostTournamentInscription.setUserId(UUID.randomUUID());

        return requestPostTournamentInscription;
    }
}