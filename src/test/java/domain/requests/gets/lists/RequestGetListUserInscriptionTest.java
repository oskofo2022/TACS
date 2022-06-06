package domain.requests.gets.lists;

import domain.persistence.entities.Tournament;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RequestGetListUserInscriptionTest extends RequestGetListOnMemoryPagedListTest<Tournament, RequestGetListUserInscription> {
    @Test
    public void sortByTournamentName() {
        this.request.setSortBy("name");

        this.valid();
    }

    @Test
    public void sortByTournamentState() {
        this.request.setSortBy("state");

        this.valid();
    }

    @Test
    public void sortByTournamentId() {
        this.request.setSortBy("id");

        this.valid();
    }

    @Test
    public void sortByTournamentStartDate() {
        this.request.setSortBy("startDate");

        this.valid();
    }

    @Test
    public void sortByTournamentEndDate() {
        this.request.setSortBy("endDate");

        this.valid();
    }

    @Test
    public void sortByInvalidValue() {
        this.request.setSortBy("invalid");

        this.invalid("", "RegexSortBy");
    }

    @Test
    public void isValidTournamentName() {
        final var tournamentName = "name";
        this.request.setTournamentName(tournamentName);

        final var tournament = new Tournament();
        tournament.setName(tournamentName);

        assertTrue(this.request.isValid(tournament));
    }

    @Test
    public void isValidTournamentId() {
        final var tournamentId = UUID.randomUUID();
        this.request.setTournamentId(tournamentId);

        final var tournament = new Tournament();
        tournament.setId(tournamentId);

        assertTrue(this.request.isValid(tournament));
    }

    @Test
    public void isValidTournamentIds() {
        final var tournamentId = UUID.randomUUID();
        this.request.setTournamentIds(List.of(tournamentId));

        final var tournament = new Tournament();
        tournament.setId(tournamentId);

        assertTrue(this.request.isValid(tournament));
    }

    @Test
    public void isValidTournamentBottomStartDate() {
        final var localDate = LocalDate.now();
        this.request.setTournamentBottomStartDate(localDate);

        final var tournament = new Tournament();
        tournament.setStartDate(localDate);

        assertTrue(this.request.isValid(tournament));
    }

    @Test
    public void isNotValidTournamentTopStartDate() {
        final var localDate = LocalDate.now();
        this.request.setTournamentTopStartDate(localDate);

        final var tournament = new Tournament();
        tournament.setStartDate(LocalDate.now());

        assertTrue(this.request.isValid(tournament));
    }

    @Test
    public void isValidTournamentBottomEndDate() {
        final var localDate = LocalDate.now();
        this.request.setTournamentBottomEndDate(localDate);

        final var tournament = new Tournament();
        tournament.setEndDate(localDate);

        assertTrue(this.request.isValid(tournament));
    }

    @Test
    public void isValidTournamentTopEndDate() {
        final var localDate = LocalDate.now();
        this.request.setTournamentTopEndDate(localDate);

        final var tournament = new Tournament();
        tournament.setEndDate(localDate);

        assertTrue(this.request.isValid(tournament));
    }

    @Test
    public void isValidTournamentLanguage() {
        final var language = Language.ENGLISH;
        this.request.setTournamentLanguage(language);

        final var tournament = new Tournament();
        tournament.setLanguage(language);

        assertTrue(this.request.isValid(tournament));
    }

    @Test
    public void isValidTournamentState() {
        this.request.setTournamentState(TournamentState.STARTED);

        final var tournament = new Tournament();
        tournament.setStartDate(LocalDate.now().minusDays(1));
        tournament.setEndDate(LocalDate.now());

        assertTrue(this.request.isValid(tournament));
    }

    @Test
    public void isValidTournamentVisibility() {
        final var visibility = Visibility.PUBLIC;
        this.request.setTournamentVisibility(visibility);

        final var tournament = new Tournament();
        tournament.setVisibility(visibility);

        assertTrue(this.request.isValid(tournament));
    }

    @Test
    @Override
    public void defaultSortBy() {
        assertEquals("name", this.request.defaultSortBy());
    }


    @Test
    @Override
    public void paginate() {
        this.request.setPage(1);
        this.request.setPageSize(3);
        this.request.setSortOrder(SortOrder.DESCENDING);
        this.request.setTournamentName("name");

        final var firstTournament = new Tournament();
        firstTournament.setName("tour-na-ment");
        firstTournament.setLanguage(Language.ENGLISH);
        firstTournament.setStartDate(LocalDate.now());
        firstTournament.setEndDate(LocalDate.now().plusDays(5));
        firstTournament.setId(UUID.randomUUID());
        firstTournament.setVisibility(Visibility.PRIVATE);

        final var secondTournament = new Tournament();
        secondTournament.setName("a name as a second");
        secondTournament.setLanguage(Language.SPANISH);
        secondTournament.setStartDate(LocalDate.now().minusDays(5));
        secondTournament.setEndDate(LocalDate.now().minusDays(1));
        secondTournament.setId(UUID.randomUUID());
        secondTournament.setVisibility(Visibility.PUBLIC);

        final var thirdTournament = new Tournament();
        thirdTournament.setName("third name");
        thirdTournament.setLanguage(Language.SPANISH);
        thirdTournament.setStartDate(LocalDate.now().minusDays(5));
        thirdTournament.setEndDate(LocalDate.now().minusDays(1));
        thirdTournament.setId(UUID.randomUUID());
        thirdTournament.setVisibility(Visibility.PUBLIC);


        final var fourthTournament = new Tournament();
        fourthTournament.setName("fourth name");
        fourthTournament.setLanguage(Language.SPANISH);
        fourthTournament.setStartDate(LocalDate.now().minusDays(5));
        fourthTournament.setEndDate(LocalDate.now().minusDays(1));
        fourthTournament.setId(UUID.randomUUID());
        fourthTournament.setVisibility(Visibility.PUBLIC);

        final var fifthTournament = new Tournament();
        fifthTournament.setName("a fifth tournament name");
        fifthTournament.setLanguage(Language.ENGLISH);
        fifthTournament.setStartDate(LocalDate.now().minusDays(5));
        fifthTournament.setEndDate(LocalDate.now().minusDays(1));
        fifthTournament.setId(UUID.randomUUID());
        fifthTournament.setVisibility(Visibility.PUBLIC);

        final var tournaments = Arrays.asList(firstTournament, secondTournament, thirdTournament, fourthTournament, fifthTournament);

        final var responseGetPagedList = this.request.paginate(tournaments::stream);

        assertEquals(2, responseGetPagedList.pageCount());
        assertEquals(4, responseGetPagedList.totalCount());
        assertEquals(3, responseGetPagedList.pageItems().size());

        final var pageItems = responseGetPagedList.pageItems();

        assertEquals(tournaments.get(2), pageItems.get(0));
        assertEquals(tournaments.get(3), pageItems.get(1));
        assertEquals(tournaments.get(1), pageItems.get(2));
    }

    @Override
    protected RequestGetListUserInscription createValidRequest() {
        return new RequestGetListUserInscription();
    }
}