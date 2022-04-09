package domain.responses.posts;

public class ResponsePostEntityCreation {
    private final long id;

    public ResponsePostEntityCreation(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
