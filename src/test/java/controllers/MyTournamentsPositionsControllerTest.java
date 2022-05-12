package controllers;

import domain.persistence.entities.Inscription;
import domain.persistence.entities.InscriptionIdentifier;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListTournamentPosition;
import domain.responses.gets.lists.ResponseGetPagedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class MyTournamentsPositionsControllerTest {

    @Mock
    private UserContextService userContextService;

    @InjectMocks
    private MyTournamentsPositionsController myTournamentsPositionsController;

    @Test
    void getList(){

        final long idUser = 1;
        final long idTournamentOne = 4;
        final long idTournamentTwo = 5;

        final var user = Mockito.mock(User.class);
        user.setId(idUser);
        user.setName("someName");
        user.setEmail("some@email.com");
        user.setPassword("someKindOfHardcorePassword");

        final var tournamentOne = new Tournament();
        tournamentOne.setName("Tournament One");
        tournamentOne.setState(TournamentState.READY);
        tournamentOne.setVisibility(Visibility.PUBLIC);
        tournamentOne.setStartDate(LocalDate.now());
        tournamentOne.setEndDate(LocalDate.now().plusMonths(1));
        tournamentOne.setLanguage(Language.SPANISH);
        tournamentOne.setUserCreator(user);

        final var tournamentTwo = new Tournament();
        tournamentTwo.setName("Tournament Two");
        tournamentTwo.setState(TournamentState.READY);
        tournamentTwo.setVisibility(Visibility.PUBLIC);
        tournamentTwo.setStartDate(LocalDate.now());
        tournamentTwo.setEndDate(LocalDate.now().plusMonths(2));
        tournamentTwo.setLanguage(Language.SPANISH);
        tournamentTwo.setUserCreator(user);

        final var requestGetListTournamentPosition = new RequestGetListTournamentPosition();
        requestGetListTournamentPosition.setTournamentName("Tournament One");
        requestGetListTournamentPosition.setTournamentState(TournamentState.READY);

        final var inscriptions = new ArrayList<Inscription>();

        InscriptionIdentifier inscriptionIdentifierOne = new InscriptionIdentifier();
        inscriptionIdentifierOne.setTournamentId(idTournamentOne);
        inscriptionIdentifierOne.setUserId(user.getId());

        Inscription inscriptionOne = new Inscription();
        inscriptionOne.setIdentifier(inscriptionIdentifierOne);
        inscriptionOne.setTournament(tournamentOne);
        inscriptionOne.setUser(user);

        tournamentOne.setInscriptions(new ArrayList<>());
        tournamentOne.addInscription(inscriptionOne);

        InscriptionIdentifier inscriptionIdentifierTwo = new InscriptionIdentifier();
        inscriptionIdentifierTwo.setTournamentId(idTournamentTwo);
        inscriptionIdentifierTwo.setUserId(user.getId());

        Inscription inscriptionTwo = new Inscription();
        inscriptionTwo.setIdentifier(inscriptionIdentifierTwo);
        inscriptionTwo.setTournament(tournamentTwo);
        inscriptionTwo.setUser(user);

        tournamentTwo.setInscriptions(new ArrayList<>());
        tournamentTwo.addInscription(inscriptionTwo);

        inscriptions.add(inscriptionOne);
        inscriptions.add(inscriptionTwo);

        final long totalElements = 1;
        final long totalPages = 1;

        Mockito.when(this.userContextService.get()).thenReturn(user);
        Mockito.when(user.getInscriptions()).thenReturn(inscriptions);

        final var responseEntity = this.myTournamentsPositionsController.list(requestGetListTournamentPosition);
        final var responseGetPagedList = responseEntity.getBody();
        final var responseGetListTournamentPosition = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(totalElements, responseGetPagedList.totalCount());
        assertEquals(totalPages, responseGetPagedList.pageCount());

        for(int i = 0; i < tournamentOne.getInscriptions().size(); i++){
            assertEquals(inscriptions.get(i).getTournament().getEndDate(), responseGetListTournamentPosition.get(i).endDate());
            assertEquals(inscriptions.get(i).getTournament().getStartDate(), responseGetListTournamentPosition.get(i).startDate());
        }
        for(int i = 0; i < tournamentTwo.getInscriptions().size(); i++){
            assertEquals(inscriptions.get(i).getTournament().getEndDate(), responseGetListTournamentPosition.get(i).endDate());
            assertEquals(inscriptions.get(i).getTournament().getStartDate(), responseGetListTournamentPosition.get(i).startDate());
        }

        Mockito.verify(this.userContextService, Mockito.times(1)).get();
    }
}
