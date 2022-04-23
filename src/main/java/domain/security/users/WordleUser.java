package domain.security.users;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class WordleUser extends User {
    public WordleUser(String username, String password, String email, long id) {
        super(username, password, new ArrayList<>());
        this.email = email;
        this.id = id;
    }

    private final String email;
    private final long id;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
