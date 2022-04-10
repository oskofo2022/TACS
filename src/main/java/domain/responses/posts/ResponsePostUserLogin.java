package domain.responses.posts;

public class ResponsePostUserLogin extends ResponsePostEntityCreation{

    private final String name;

    public ResponsePostUserLogin(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
