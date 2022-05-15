package controllers;

import constants.UriConstants;
import domain.persistence.entities.Inscription;
import domain.persistence.entities.InscriptionIdentifier;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.repositories.InscriptionRepository;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import domain.persistence.sessions.UserContextService;
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

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TournamentInscriptionsControllerTest {
/*
    @Mock
    private UserRepository userRepository;

    @Mock
    private WordleAuthenticationManager wordleAuthenticationManager;

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private InscriptionRepository inscriptionRepository;

    @InjectMocks
    private TournamentInscriptionsController tournamentInscriptionsController;

    @Test
    void postInscriptionPublicTournament(){

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI(UriConstants.DELIMITER + UriConstants.Tournaments.Public.URL + UriConstants.Tournaments.Public.Inscriptions.CURRENT_USER);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        final long idUser = 1;
        final long idTournament = 4;

        final var tournament = new Tournament();
        tournament.setName("Example Tournament");
        tournament.setState(TournamentState.READY);
        tournament.setVisibility(Visibility.PUBLIC);
        tournament.setStartDate(LocalDate.now().plusMonths(2));
        tournament.setEndDate(LocalDate.now().plusMonths(4));
        tournament.setLanguage(Language.ENGLISH);
        tournament.setId(idTournament);

        final var user = new User();
        user.setId(idUser);
        user.setName("someName");
        user.setEmail("some@email.com");
        user.setPassword("pass");

        var wordleUser = new WordleUser("someName", "pass", "some@email.com", idUser);

        final Supplier<Inscription> getArgumentMatcherInscription = () -> Mockito.argThat((Inscription i) -> i.getTournament().equals(tournament)
                                                                        && i.getUser().equals(user));

        var inscriptionIdentifier = new InscriptionIdentifier();
        inscriptionIdentifier.setTournamentId(idTournament);
        inscriptionIdentifier.setUserId(idUser);

        Mockito.when(this.wordleAuthenticationManager.getCurrentUser()).thenReturn(wordleUser);
        Mockito.when(this.userRepository.findById(idUser)).thenReturn(Optional.of(user));
        Mockito.when(this.tournamentRepository.findById(tournament.getId())).thenReturn(Optional.of(tournament));
        Mockito.when(this.inscriptionRepository.save(getArgumentMatcherInscription.get())).then((iom -> {
                final var inscription = iom.getArgument(0,Inscription.class);
                inscription.setTournament(tournament);
                inscription.setUser(user);
                inscription.setIdentifier(inscriptionIdentifier);
                return inscription;
        }));

        var responseEntity = this.tournamentInscriptionsController.post(idTournament);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders().get("Location"));
        assertEquals("http://localhost/users/myself/inscriptions/tournaments?tournamentId=4", responseEntity.getHeaders().get("Location").get(0));

        Mockito.verify(this.wordleAuthenticationManager, Mockito.times(1)).getCurrentUser();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(idUser);
        Mockito.verify(this.tournamentRepository, Mockito.times(1)).findById(idTournament);
        Mockito.verify(this.inscriptionRepository, Mockito.times(1)).save(getArgumentMatcherInscription.get());
    }

    @Test
    void postInscriptionTournament(){

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI(UriConstants.DELIMITER + UriConstants.Tournaments.Inscriptions.URL);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        final long idUserCreator = 1;
        final long idTournament = 4;

        final var user = new User();
        user.setId(idUserCreator);
        user.setName("someName");
        user.setEmail("some@email.com");
        user.setPassword("pass");

        final var tournament = new Tournament();
        tournament.setName("Private Example Tournament");
        tournament.setState(TournamentState.READY);
        tournament.setVisibility(Visibility.PRIVATE);
        tournament.setStartDate(LocalDate.now().plusMonths(2));
        tournament.setEndDate(LocalDate.now().plusMonths(4));
        tournament.setLanguage(Language.SPANISH);
        tournament.setId(idTournament);
        tournament.setUserCreator(user);

        var requestPostTournamentInscription = new RequestPostTournamentInscription();
        requestPostTournamentInscription.setUserId(idUserCreator);

        var wordleUser = new WordleUser("someName", "pass", "some@email.com", idUserCreator);

        final Supplier<Inscription> getArgumentMatcherInscription = () -> Mockito.argThat((Inscription i) -> i.getTournament().equals(tournament)
                && i.getUser().getId() == requestPostTournamentInscription.getUserId());

        var inscriptionIdentifier = new InscriptionIdentifier();
        inscriptionIdentifier.setTournamentId(idTournament);
        inscriptionIdentifier.setUserId(idUserCreator);

        Mockito.when(this.wordleAuthenticationManager.getCurrentUser()).thenReturn(wordleUser);
        Mockito.when(this.userRepository.findById(idUserCreator)).thenReturn(Optional.of(user));
        Mockito.when(this.tournamentRepository.findById(idTournament)).thenReturn(Optional.of(tournament));
        Mockito.when(this.inscriptionRepository.save(getArgumentMatcherInscription.get())).then((iom -> {
            final var inscription = iom.getArgument(0,Inscription.class);
            inscription.setTournament(tournament);
            inscription.setUser(user);
            inscription.setIdentifier(inscriptionIdentifier);
            return inscription;
        }));

        var responseEntity = this.tournamentInscriptionsController.post(idTournament, requestPostTournamentInscription);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders().get("Location"));
        assertEquals("http://localhost/users/myself/inscriptions/tournaments?tournamentId=4", responseEntity.getHeaders().get("Location").get(0));

        Mockito.verify(this.wordleAuthenticationManager, Mockito.times(1)).getCurrentUser();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(idUserCreator);
        Mockito.verify(this.tournamentRepository, Mockito.times(1)).findById(idTournament);
        Mockito.verify(this.inscriptionRepository, Mockito.times(1)).save(getArgumentMatcherInscription.get());
    }


 */
}
