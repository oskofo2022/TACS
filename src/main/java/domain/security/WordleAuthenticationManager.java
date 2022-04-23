package domain.security;

import domain.security.users.WordleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public record WordleAuthenticationManager(AuthenticationManager authenticationManager) {

    @Autowired
    public WordleAuthenticationManager {
    }

    public WordleUser authenticate(String email, String password) {
        var authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return (WordleUser) authentication.getPrincipal();
    }

    public WordleUser getCurrentUser() {
        return (WordleUser) SecurityContextHolder.getContext()
                                                 .getAuthentication()
                                                 .getPrincipal();
    }
}
