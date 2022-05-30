package domain.responses.posts;

import java.util.UUID;

public class ResponsePostEntityCreation {
    private final UUID id;

    public ResponsePostEntityCreation(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
