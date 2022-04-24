package domain.security;

import constants.RSQLConstants;
import domain.errors.runtime.LoginCredentialsRuntimeException;
import domain.persistence.repositories.UserRepository;
import domain.security.users.WordleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;

@Service
public class WordleUserDetails implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public WordleUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = this.userRepository.findAll(toSpecification(RSQLConstants.Filters.getLike("email").formatted(username)))
                                                  .stream()
                                                  .findFirst()
                                                  .orElseThrow(LoginCredentialsRuntimeException::new);

        return new WordleUser(user.getName(), user.getPassword(), user.getEmail(), user.getId());
    }
}
