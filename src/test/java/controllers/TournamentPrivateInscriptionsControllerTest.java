package controllers;

import constants.SuppressWarningsConstants;
import constants.UriConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import domain.requests.posts.RequestPostTournamentInscription;
import domain.security.WordleAuthenticationManager;
import domain.security.users.WordleUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
class TournamentPrivateInscriptionsControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WordleAuthenticationManager wordleAuthenticationManager;

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private TournamentPrivateInscriptionsController tournamentPrivateInscriptionsController;

    @Test
    void post() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI(UriConstants.DELIMITER + UriConstants.Tournaments.Inscriptions.URL);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        final var userCreatorId = UUID.randomUUID();
        final var tournamentId = UUID.randomUUID();

        final var user = new User();
        user.setId(userCreatorId);
        user.setName("someName");
        user.setEmail("some@email.com");
        user.setPassword("pass");

        final var tournament = Mockito.mock(Tournament.class);

        var requestPostTournamentInscription = new RequestPostTournamentInscription();
        requestPostTournamentInscription.setUserId(userCreatorId);

        var wordleUser = new WordleUser("someName", "pass", "some@email.com", userCreatorId);

        Mockito.when(tournament.getId()).thenReturn(tournamentId);
        Mockito.when(this.wordleAuthenticationManager.getCurrentUser()).thenReturn(wordleUser);
        Mockito.when(this.userRepository.findById(userCreatorId)).thenReturn(Optional.of(user));
        Mockito.when(this.tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        var responseEntity = this.tournamentPrivateInscriptionsController.post(tournamentId, requestPostTournamentInscription);
        final var headerLocation = responseEntity.getHeaders().get("Location");

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(headerLocation);
        assertEquals("http://localhost/users/myself/inscriptions/tournaments?tournamentId=%s".formatted(tournamentId.toString()), headerLocation.get(0));

        Mockito.verify(tournament, Mockito.times(1)).getId();
        Mockito.verify(tournament, Mockito.times(1)).validateAuthority(Mockito.any());
        Mockito.verify(tournament, Mockito.times(1)).inscribe(user);
        Mockito.verify(this.wordleAuthenticationManager, Mockito.times(1)).getCurrentUser();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(userCreatorId);
        Mockito.verify(this.tournamentRepository, Mockito.times(1)).findById(tournamentId);
        Mockito.verify(this.tournamentRepository, Mockito.times(1)).save(tournament);
    }

    @Test
    void postTournamentNotFound() {
        final var userCreatorId = UUID.randomUUID();
        final var tournamentId = UUID.randomUUID();

        var requestPostTournamentInscription = new RequestPostTournamentInscription();
        requestPostTournamentInscription.setUserId(userCreatorId);

        Mockito.when(this.tournamentRepository.findById(tournamentId)).thenReturn(Optional.empty());

        var entityNotFoundRuntimeException = assertThrows(EntityNotFoundRuntimeException.class, () -> this.tournamentPrivateInscriptionsController.post(tournamentId, requestPostTournamentInscription));

        assertEquals(entityNotFoundRuntimeException.getCode(), "TOURNAMENT_NOT_FOUND");
        assertEquals(entityNotFoundRuntimeException.getMessage(), "La entidad Tournament no pudo ser encontrada");

        Mockito.verify(this.tournamentRepository, Mockito.times(1)).findById(tournamentId);
        Mockito.verify(this.userRepository, Mockito.never()).findById(Mockito.any());
        Mockito.verify(this.wordleAuthenticationManager, Mockito.never()).getCurrentUser();
        Mockito.verify(this.tournamentRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void postUserNotFound() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI(UriConstants.DELIMITER + UriConstants.Tournaments.Inscriptions.URL);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        final var userCreatorId = UUID.randomUUID();
        final var tournamentId = UUID.randomUUID();

        final var user = new User();
        user.setId(userCreatorId);
        user.setName("someName");
        user.setEmail("some@email.com");
        user.setPassword("pass");

        final var tournament = Mockito.mock(Tournament.class);

        var requestPostTournamentInscription = new RequestPostTournamentInscription();
        requestPostTournamentInscription.setUserId(userCreatorId);

        Mockito.when(this.userRepository.findById(userCreatorId)).thenReturn(Optional.empty());
        Mockito.when(this.tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        var entityNotFoundRuntimeException = assertThrows(EntityNotFoundRuntimeException.class, () -> this.tournamentPrivateInscriptionsController.post(tournamentId, requestPostTournamentInscription));

        assertEquals(entityNotFoundRuntimeException.getCode(), "USER_NOT_FOUND");
        assertEquals(entityNotFoundRuntimeException.getMessage(), "La entidad User no pudo ser encontrada");

        Mockito.verify(tournament, Mockito.never()).getId();
        Mockito.verify(tournament, Mockito.never()).validateAuthority(Mockito.any());
        Mockito.verify(tournament, Mockito.never()).inscribe(user);
        Mockito.verify(this.wordleAuthenticationManager, Mockito.never()).getCurrentUser();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(userCreatorId);
        Mockito.verify(this.tournamentRepository, Mockito.times(1)).findById(tournamentId);
        Mockito.verify(this.tournamentRepository, Mockito.never()).save(Mockito.any());
    }
}