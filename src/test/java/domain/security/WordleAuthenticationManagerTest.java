package domain.security;

import domain.security.users.WordleUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WordleAuthenticationManagerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private WordleAuthenticationManager wordleAuthenticationManager;

    @Test
    void authenticate() {
        final var email = "some@email.com";
        final var password = "strongPassword";

        final var wordleUser = new WordleUser("name", "strongPassword", email, UUID.randomUUID());

        final var authentication = Mockito.mock(Authentication.class);

        final Supplier<UsernamePasswordAuthenticationToken> getArgumentMatcherUsernamePasswordAuthenticationToken = () -> Mockito.argThat((UsernamePasswordAuthenticationToken upat) -> upat.getPrincipal().equals(email) && upat.getCredentials().equals(password));

        Mockito.when(authentication.getPrincipal()).thenReturn(wordleUser);
        Mockito.when(this.authenticationManager.authenticate(getArgumentMatcherUsernamePasswordAuthenticationToken.get())).thenReturn(authentication);

        final var user = this.wordleAuthenticationManager.authenticate(email, password);

        assertEquals(wordleUser, user);

        Mockito.verify(authentication, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.authenticationManager, Mockito.times(1)).authenticate(getArgumentMatcherUsernamePasswordAuthenticationToken.get());
    }

    @Test
    void getCurrentUser() {
        final var wordleUser = new WordleUser("name", "strongPassword", "some@email.com", UUID.randomUUID());

        Authentication authentication = Mockito.mock(Authentication.class);

        final var securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(authentication.getPrincipal()).thenReturn(wordleUser);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        final var user = this.wordleAuthenticationManager.getCurrentUser();

        assertEquals(user, wordleUser);

        Mockito.verify(authentication, Mockito.times(1)).getPrincipal();
        Mockito.verify(securityContext, Mockito.times(1)).getAuthentication();
    }
}