package domain.security;

import domain.errors.runtime.LoginCredentialsRuntimeException;
import domain.persistence.entities.User;
import domain.persistence.repositories.UserRepository;
import domain.security.users.WordleUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WordleUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WordleUserDetailsService wordleUserDetailsService;

    @Test
    void loadUserByUsername() {
        final var email = "some@email.com";

        final var user = new User();
        user.setName("name");
        user.setEmail(email);
        user.setId(UUID.randomUUID());
        user.setPassword("strongPassword");

        Mockito.when(this.userRepository.findAll()).thenReturn(List.of(user));

        final var userDetails = this.wordleUserDetailsService.loadUserByUsername(email);

        final var  wordleUser = assertInstanceOf(WordleUser.class, userDetails);

        assertEquals(user.getName(), wordleUser.getUsername());
        assertEquals(user.getPassword(), wordleUser.getPassword());
        assertEquals(user.getId(), wordleUser.getId());
        assertEquals(user.getEmail(), wordleUser.getEmail());

        Mockito.verify(this.userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void loadUserByUsernameUserNotFound() {
        final var user = new User();
        user.setName("name");
        user.setEmail("some@email.com");
        user.setId(UUID.randomUUID());
        user.setPassword("strongPassword");

        Mockito.when(this.userRepository.findAll()).thenReturn(List.of(user));

        assertThrows(LoginCredentialsRuntimeException.class, () -> this.wordleUserDetailsService.loadUserByUsername("some@email2.com"));

        Mockito.verify(this.userRepository, Mockito.times(1)).findAll();
    }
}