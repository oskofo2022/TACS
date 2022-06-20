package domain.persistence.sessions;

import domain.errors.constants.ErrorMessageConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.User;
import domain.persistence.repositories.UserRepository;
import domain.security.WordleAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContextService {

    private final UserRepository userRepository;
    private final WordleAuthenticationManager wordleAuthenticationManager;

    @Autowired
    public UserContextService(UserRepository userRepository, WordleAuthenticationManager wordleAuthenticationManager) {
        this.userRepository = userRepository;
        this.wordleAuthenticationManager = wordleAuthenticationManager;
    }

    public User get() {
        final var wordleUser = this.wordleAuthenticationManager.getCurrentUser();
        return this.userRepository.findById(wordleUser.getId())
                                  .orElseThrow(() -> new EntityNotFoundRuntimeException(ErrorMessageConstants.Entities.Names.USER, User.class));
    }
}
