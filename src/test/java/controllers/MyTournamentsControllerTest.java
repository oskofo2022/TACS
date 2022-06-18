package controllers;

import constants.SuppressWarningsConstants;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.repositories.TournamentRepository;
import domain.requests.gets.lists.RequestGetListMyTournament;
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
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
class MyTournamentsControllerTest {
    @Mock
    private WordleAuthenticationManager wordleAuthenticationManager;

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private MyTournamentsController myTournamentsController;

    @Test
    void list() {
        final var userCreator = new User();
        userCreator.setId(UUID.randomUUID());

        final var firstTournament = new Tournament();
        firstTournament.setName("tournament 1");
        firstTournament.setVisibility(Visibility.PRIVATE);
        firstTournament.setLanguage(Language.ENGLISH);
        firstTournament.setStartDate(LocalDate.now().plusDays(255));
        firstTournament.setEndDate(LocalDate.now().plusDays(280));
        firstTournament.setId(UUID.randomUUID());
        firstTournament.setUserCreator(userCreator);

        final var secondTournament = new Tournament();
        secondTournament.setName("tournament 2");
        secondTournament.setVisibility(Visibility.PUBLIC);
        secondTournament.setLanguage(Language.SPANISH);
        secondTournament.setStartDate(LocalDate.of(2022, 3, 1));
        secondTournament.setEndDate(LocalDate.of(2022, 3, 31));
        secondTournament.setId(UUID.randomUUID());
        secondTournament.setUserCreator(userCreator);

        final var tournaments = new ArrayList<Tournament>() {
            {
                add(firstTournament);
                add(secondTournament);
            }
        };

        final long totalElements = 2;
        final var totalPages = 1;


        final var requestGetListMyTournament = new RequestGetListMyTournament();
        requestGetListMyTournament.setName("tournament");

        final var wordleUser = new WordleUser("someName", "pass", "someEmail@email.com", userCreator.getId());

        final var tournamentPage = Mockito.mock(Page.class);
        Mockito.when(tournamentPage.getTotalElements()).thenReturn(totalElements);
        Mockito.when(tournamentPage.getTotalPages()).thenReturn(totalPages);
        Mockito.when(tournamentPage.stream()).thenReturn(tournaments.stream());
        Mockito.when(this.wordleAuthenticationManager.getCurrentUser()).thenReturn(wordleUser);
        Mockito.when(this.tournamentRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class))).thenReturn(tournamentPage);

        final var responseEntity =  this.myTournamentsController.list(requestGetListMyTournament);
        final var responseGetPagedList = responseEntity.getBody();
        final var responsesGetListTournament = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(totalElements, responseGetPagedList.totalCount());
        assertEquals(totalPages, responseGetPagedList.pageCount());

        for (var index = 0; index < tournaments.size(); index++) {
            assertEquals(tournaments.get(index).getId(), responsesGetListTournament.get(index).id());
            assertEquals(tournaments.get(index).getName(), responsesGetListTournament.get(index).name());
            assertEquals(tournaments.get(index).getVisibility(), responsesGetListTournament.get(index).visibility());
            assertEquals(tournaments.get(index).getLanguage(), responsesGetListTournament.get(index).language());
            assertEquals(tournaments.get(index).getState(), responsesGetListTournament.get(index).state());
            assertEquals(tournaments.get(index).getStartDate(), responsesGetListTournament.get(index).startDate());
            assertEquals(tournaments.get(index).getEndDate(), responsesGetListTournament.get(index).endDate());
        }

        Mockito.verify(tournamentPage, Mockito.times(1)).getTotalElements();
        Mockito.verify(tournamentPage, Mockito.times(1)).getTotalPages();
        Mockito.verify(tournamentPage, Mockito.times(1)).stream();
        Mockito.verify(this.wordleAuthenticationManager, Mockito.times(1)).getCurrentUser();
        Mockito.verify(this.tournamentRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class));
    }

    @Test
    void listWithoutFilter() {
        final var userCreator = new User();
        userCreator.setId(UUID.randomUUID());

        final var firstTournament = new Tournament();
        firstTournament.setName("tournament 1");
        firstTournament.setVisibility(Visibility.PRIVATE);
        firstTournament.setLanguage(Language.ENGLISH);
        firstTournament.setStartDate(LocalDate.now().plusDays(255));
        firstTournament.setEndDate(LocalDate.now().plusDays(280));
        firstTournament.setId(UUID.randomUUID());
        firstTournament.setUserCreator(userCreator);

        final var secondTournament = new Tournament();
        secondTournament.setName("tournament 2");
        secondTournament.setVisibility(Visibility.PUBLIC);
        secondTournament.setLanguage(Language.SPANISH);
        secondTournament.setStartDate(LocalDate.of(2022, 3, 1));
        secondTournament.setEndDate(LocalDate.of(2022, 3, 31));
        secondTournament.setId(UUID.randomUUID());
        secondTournament.setUserCreator(userCreator);

        final var tournaments = new ArrayList<Tournament>() {
            {
                add(firstTournament);
                add(secondTournament);
            }
        };

        final long totalElements = 2;
        final var totalPages = 1;


        final var requestGetListMyTournament = new RequestGetListMyTournament();
        requestGetListMyTournament.setName("tournament");

        final var wordleUser = new WordleUser("someName", "pass", "someEmail@email.com", userCreator.getId());

        final var userPage = Mockito.mock(Page.class);
        Mockito.when(userPage.getTotalElements()).thenReturn(totalElements);
        Mockito.when(userPage.getTotalPages()).thenReturn(totalPages);
        Mockito.when(userPage.stream()).thenReturn(tournaments.stream());
        Mockito.when(this.wordleAuthenticationManager.getCurrentUser()).thenReturn(wordleUser);
        Mockito.when(this.tournamentRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class))).thenReturn(userPage);

        final var responseEntity =  this.myTournamentsController.list(requestGetListMyTournament);
        final var responseGetPagedList = responseEntity.getBody();
        final var responsesGetListTournament = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(totalElements, responseGetPagedList.totalCount());
        assertEquals(totalPages, responseGetPagedList.pageCount());

        for (var index = 0; index < tournaments.size(); index++) {
            assertEquals(tournaments.get(index).getId(), responsesGetListTournament.get(index).id());
            assertEquals(tournaments.get(index).getName(), responsesGetListTournament.get(index).name());
            assertEquals(tournaments.get(index).getVisibility(), responsesGetListTournament.get(index).visibility());
            assertEquals(tournaments.get(index).getLanguage(), responsesGetListTournament.get(index).language());
            assertEquals(tournaments.get(index).getState(), responsesGetListTournament.get(index).state());
            assertEquals(tournaments.get(index).getStartDate(), responsesGetListTournament.get(index).startDate());
            assertEquals(tournaments.get(index).getEndDate(), responsesGetListTournament.get(index).endDate());
        }

        Mockito.verify(userPage, Mockito.times(1)).getTotalElements();
        Mockito.verify(userPage, Mockito.times(1)).getTotalPages();
        Mockito.verify(userPage, Mockito.times(1)).stream();
        Mockito.verify(this.wordleAuthenticationManager, Mockito.times(1)).getCurrentUser();
        Mockito.verify(this.tournamentRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class));
    }
}