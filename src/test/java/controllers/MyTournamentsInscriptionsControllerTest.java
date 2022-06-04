package controllers;

import constants.SuppressWarningsConstants;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListTournamentPosition;
import domain.requests.gets.lists.RequestGetListUserInscription;
import domain.security.WordleAuthenticationManager;
import domain.security.users.WordleUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
public class MyTournamentsInscriptionsControllerTest {

    @Mock
    private UserContextService userContextService;

    @InjectMocks
    private MyTournamentInscriptionsController myTournamentInscriptionsController;

    @Test
    void list() {
        final var firstTournament = new Tournament();
        firstTournament.setId(UUID.randomUUID());
        firstTournament.setName("First Tournament");
        firstTournament.setVisibility(Visibility.PUBLIC);
        firstTournament.setStartDate(LocalDate.now().plusDays(1));
        firstTournament.setEndDate(LocalDate.now().plusDays(6));
        firstTournament.setLanguage(Language.SPANISH);

        final var secondTournament = new Tournament();
        secondTournament.setId(UUID.randomUUID());
        secondTournament.setName("Second Tournament");
        secondTournament.setVisibility(Visibility.PRIVATE);
        secondTournament.setStartDate(LocalDate.now().minusDays(60));
        secondTournament.setEndDate(LocalDate.now().minusDays(55));
        secondTournament.setLanguage(Language.ENGLISH);

        final var inscribedTournaments = Arrays.asList(firstTournament, secondTournament);

        final var user = new User();
        user.setInscribedTournaments(inscribedTournaments);

        var requestGetListUserInscription = new RequestGetListUserInscription();

        Mockito.when(this.userContextService.get()).thenReturn(user);

        final var responseEntity = this.myTournamentInscriptionsController.list(requestGetListUserInscription);
        final var responseGetPagedList = responseEntity.getBody();
        final var responseGetListUserInscriptions = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(inscribedTournaments.size(), responseGetPagedList.totalCount());
        assertEquals(1, responseGetPagedList.pageCount());

        for(int i = 0; i < inscribedTournaments.size(); i++){
            assertEquals(inscribedTournaments.get(i).getId(), responseGetListUserInscriptions.get(i).tournament().id());
            assertEquals(inscribedTournaments.get(i).getName(), responseGetListUserInscriptions.get(i).tournament().name());
            assertEquals(inscribedTournaments.get(i).getState(), responseGetListUserInscriptions.get(i).tournament().state());
            assertEquals(inscribedTournaments.get(i).getVisibility(), responseGetListUserInscriptions.get(i).tournament().visibility());
            assertEquals(inscribedTournaments.get(i).getStartDate(), responseGetListUserInscriptions.get(i).tournament().startDate());
            assertEquals(inscribedTournaments.get(i).getEndDate(), responseGetListUserInscriptions.get(i).tournament().endDate());
            assertEquals(inscribedTournaments.get(i).getLanguage(), responseGetListUserInscriptions.get(i).tournament().language());
        }

        Mockito.verify(this.userContextService, Mockito.times(1)).get();
    }
}
