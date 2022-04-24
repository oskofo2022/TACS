package domain.security.contracts;

import domain.persistence.entities.User;
import domain.security.users.WordleUser;
import org.springframework.http.ResponseCookie;

public interface SessionCreator {
    ResponseCookie generateResponseCookie(WordleUser wordleUser);
}
