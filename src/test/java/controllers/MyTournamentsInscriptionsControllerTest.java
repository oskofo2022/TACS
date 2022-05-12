package controllers;

import domain.persistence.entities.Inscription;
import domain.persistence.entities.InscriptionIdentifier;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.repositories.InscriptionRepository;
import domain.persistence.repositories.TournamentRepository;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MyTournamentsInscriptionsControllerTest {

    @Mock
    private WordleAuthenticationManager wordleAuthenticationManager;

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private InscriptionRepository inscriptionRepository;

    @InjectMocks
    private MyTournamentInscriptionsController myTournamentInscriptionsController;

    @Test
    void getList(){

        final long idUser = 1;
        final long idTournament = 4;

        var userCreator = new User();
        userCreator.setPassword("pass");
        userCreator.setEmail("some@email.com");
        userCreator.setName("someName");
        userCreator.setId(idUser);

        final var wordleUser = new WordleUser("someName", "pass", "some@email.com", idUser);

        final var tournament = new Tournament();
        tournament.setName("Tournament");
        tournament.setId(idTournament);
        tournament.setState(TournamentState.READY);
        tournament.setVisibility(Visibility.PUBLIC);
        tournament.setStartDate(LocalDate.now());
        tournament.setEndDate(LocalDate.now().plusMonths(1));
        tournament.setLanguage(Language.SPANISH);
        tournament.setUserCreator(userCreator);

        final var requestGetListUserInscription = new RequestGetListUserInscription();
        requestGetListUserInscription.setUserId(wordleUser.getId());
        requestGetListUserInscription.setTournamentId(idTournament);
        requestGetListUserInscription.setTournamentName("Tournament");
        requestGetListUserInscription.setTournamentVisibility(Visibility.PUBLIC);
        requestGetListUserInscription.setTournamentState(TournamentState.READY);
        requestGetListUserInscription.setTournamentLanguage(Language.SPANISH);

        InscriptionIdentifier inscriptionIdentifierOne = new InscriptionIdentifier();
        inscriptionIdentifierOne.setTournamentId(tournament.getId());
        inscriptionIdentifierOne.setUserId(userCreator.getId());

        final var inscriptionOne = new Inscription();
        inscriptionOne.setTournament(tournament);
        inscriptionOne.setUser(userCreator);
        inscriptionOne.setIdentifier(inscriptionIdentifierOne);

        InscriptionIdentifier inscriptionIdentifierTwo = new InscriptionIdentifier();
        inscriptionIdentifierTwo.setTournamentId(tournament.getId());
        inscriptionIdentifierTwo.setUserId(userCreator.getId());

        final var inscriptionTwo = new Inscription();
        inscriptionTwo.setTournament(tournament);
        inscriptionTwo.setUser(userCreator);
        inscriptionTwo.setIdentifier(inscriptionIdentifierTwo);

        final var inscriptions = new ArrayList<Inscription>();
        inscriptions.add(inscriptionOne);
        inscriptions.add(inscriptionTwo);

        tournament.setInscriptions(inscriptions);

        final long totalElements = 2;
        final int totalPages = 1;

        final var tournamentInscriptionsPage = Mockito.mock(Page.class);
        Mockito.when(this.wordleAuthenticationManager.getCurrentUser()).thenReturn(wordleUser);
        Mockito.when(tournamentInscriptionsPage.getTotalPages()).thenReturn(totalPages);
        Mockito.when(tournamentInscriptionsPage.getTotalElements()).thenReturn(totalElements);
        Mockito.when(tournamentInscriptionsPage.stream()).thenReturn(tournament.getInscriptions().stream());
        Mockito.when(this.inscriptionRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class))).thenReturn(tournamentInscriptionsPage);

        final var responseEntity = this.myTournamentInscriptionsController.list(requestGetListUserInscription);
        final var responseGetPagedList = responseEntity.getBody();
        final var responseGetListUserInscriptions = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(totalElements, responseGetPagedList.totalCount());
        assertEquals(totalPages, responseGetPagedList.pageCount());

        for(int i = 0; i < inscriptions.size(); i++){
            assertEquals(inscriptions.get(i).getTournament().getName(), responseGetListUserInscriptions.get(i).tournament().Name());
            assertEquals(inscriptions.get(i).getTournament().getStartDate(), responseGetListUserInscriptions.get(i).tournament().startDate());
            assertEquals(inscriptions.get(i).getTournament().getEndDate(), responseGetListUserInscriptions.get(i).tournament().endDate());
            assertEquals(inscriptions.get(i).getTournament().getLanguage(), responseGetListUserInscriptions.get(i).tournament().language());
            assertEquals(inscriptions.get(i).getTournament().getVisibility(), responseGetListUserInscriptions.get(i).tournament().visibility());
            assertEquals(inscriptions.get(i).getTournament().getId(), responseGetListUserInscriptions.get(i).tournament().id());
            assertEquals(inscriptions.get(i).getTournament().getState(), responseGetListUserInscriptions.get(i).tournament().tournamentState());
        }

        Mockito.verify(tournamentInscriptionsPage,Mockito.times(1)).getTotalPages();
        Mockito.verify(tournamentInscriptionsPage,Mockito.times(1)).getTotalElements();
        Mockito.verify(tournamentInscriptionsPage,Mockito.times(1)).stream();
        Mockito.verify(this.inscriptionRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class),Mockito.any(PageRequest.class));
    }
}
