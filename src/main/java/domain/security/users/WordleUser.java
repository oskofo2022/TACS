package domain.security.users;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.UUID;

public class WordleUser extends User {
    public WordleUser(String username, String password, String email, UUID id) {
        super(username, password, new ArrayList<>());
        this.email = email;
        this.id = id;
    }

    private final String email;
    private final UUID id;

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
