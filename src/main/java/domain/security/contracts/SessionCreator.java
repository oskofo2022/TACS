package domain.security.contracts;

import domain.persistence.entities.User;
import org.springframework.http.ResponseCookie;

public interface SessionCreator {
    ResponseCookie generateResponseCookie(User user);
}
