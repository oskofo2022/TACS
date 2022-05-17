package controllers;

import constants.SuppressWarningsConstants;
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
import domain.persistence.sessions.UserContextService;
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
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
class TournamentPublicInscriptionsControllerTest {
    @Mock
    private UserContextService userContextService;

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private InscriptionRepository inscriptionRepository;

    @InjectMocks
    private TournamentPublicInscriptionsController tournamentPublicInscriptionsController;

    @Test
    void post() {

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

        final Supplier<Inscription> getArgumentMatcherInscription = () -> Mockito.argThat((Inscription i) -> i.getTournament().equals(tournament)
                                                                                           && i.getUser().equals(user));

        var inscriptionIdentifier = new InscriptionIdentifier();
        inscriptionIdentifier.setTournamentId(idTournament);
        inscriptionIdentifier.setUserId(idUser);
        final var inscription = new Inscription();
        inscription.setIdentifier(inscriptionIdentifier);
        final var inscriptions = List.of(inscription);

        Mockito.when(this.userContextService.get()).thenReturn(user);
        Mockito.when(this.tournamentRepository.findById(tournament.getId())).thenReturn(Optional.of(tournament));

        var responseEntity = this.tournamentPublicInscriptionsController.post(idTournament);
        final var headerLocation = responseEntity.getHeaders().get("Location");

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(headerLocation);
        assertEquals("http://localhost/users/myself/inscriptions/tournaments?tournamentId=4", headerLocation.get(0));

        Mockito.verify(this.userContextService, Mockito.times(1)).get();
        Mockito.verify(this.tournamentRepository, Mockito.times(1)).findById(idTournament);
        Mockito.verify(this.inscriptionRepository, Mockito.times(1)).save(getArgumentMatcherInscription.get());
    }

    //TODO: Test duplicate case
}