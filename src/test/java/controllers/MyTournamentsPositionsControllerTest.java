package controllers;

import constants.SuppressWarningsConstants;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListTournamentPosition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
public class MyTournamentsPositionsControllerTest {

    @Mock
    private UserContextService userContextService;

    @InjectMocks
    private MyTournamentsPositionsController myTournamentsPositionsController;

    @Test
    void list() {

        final var user = new User();
        user.setMatches(Collections.emptyList());

        final var firstTournament = new Tournament();
        firstTournament.setId(UUID.randomUUID());
        firstTournament.setName("First Tournament");
        firstTournament.setVisibility(Visibility.PUBLIC);
        firstTournament.setStartDate(LocalDate.now().plusDays(1));
        firstTournament.setEndDate(LocalDate.now().plusDays(5));
        firstTournament.setLanguage(Language.SPANISH);
        firstTournament.setPlayers(List.of(user));

        final var secondTournament = new Tournament();
        secondTournament.setId(UUID.randomUUID());
        secondTournament.setName("Second Tournament");
        secondTournament.setVisibility(Visibility.PRIVATE);
        secondTournament.setStartDate(LocalDate.now().minusDays(60));
        secondTournament.setEndDate(LocalDate.now().minusDays(55));
        secondTournament.setLanguage(Language.ENGLISH);
        secondTournament.setPlayers(List.of(user));

        final var inscribedTournaments = Arrays.asList(firstTournament, secondTournament);

        user.setInscribedTournaments(inscribedTournaments);

        var requestGetListTournamentPosition = new RequestGetListTournamentPosition();

        Mockito.when(this.userContextService.get()).thenReturn(user);

        final var responseEntity = this.myTournamentsPositionsController.list(requestGetListTournamentPosition);
        final var responseGetPagedList = responseEntity.getBody();
        final var responsesGetListTournamentPosition = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(inscribedTournaments.size(), responseGetPagedList.totalCount());
        assertEquals(1, responseGetPagedList.pageCount());

        for (int i = 0; i < inscribedTournaments.size(); i++) {
            final var tournament = inscribedTournaments.get(i);
            final var expectedPositions = tournament.listPositions().toList();

            final var responseGetListTournamentPosition = responsesGetListTournamentPosition.get(i);
            final var actualPositions = responseGetListTournamentPosition.positions();

            assertEquals(tournament.getId(), responseGetListTournamentPosition.id());
            assertEquals(tournament.getName(), responseGetListTournamentPosition.name());
            assertEquals(tournament.getState(), responseGetListTournamentPosition.state());
            assertEquals(tournament.getVisibility(), responseGetListTournamentPosition.visibility());
            assertEquals(tournament.getStartDate(), responseGetListTournamentPosition.startDate());
            assertEquals(tournament.getEndDate(), responseGetListTournamentPosition.endDate());
            assertEquals(tournament.getLanguage(), responseGetListTournamentPosition.language());
            for (int j = 0; i < expectedPositions.size(); i++) {
                assertEquals(expectedPositions.get(j).getName(), actualPositions.get(j).getName());
                assertEquals(expectedPositions.get(j).getGuessesCount(), actualPositions.get(j).getGuessesCount());
                assertEquals(expectedPositions.get(j).getCardinal(), actualPositions.get(j).getCardinal());
            }
        }

        Mockito.verify(this.userContextService, Mockito.times(1)).get();
    }
}
