package domain.persistence.entities;

import domain.errors.runtime.DuplicateEntityFoundRuntimeException;
import domain.errors.runtime.TournamentStateInvalidInscriptionRuntimeException;
import domain.errors.runtime.TournamentUnauthorizedUserActionRuntimeException;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.responses.gets.lists.ResponseGetListTournamentPositionResult;
import domain.security.users.WordleUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TournamentTest {

    @InjectMocks
    private Tournament tournament;

    @Test
    void inscribe() {
        final var user = new User();
        user.setId(UUID.randomUUID());
        this.tournament.setId(UUID.randomUUID());
        this.tournament.setPlayers(new ArrayList<>());
        this.tournament.setStartDate(LocalDate.now().plusDays(1));

        assertDoesNotThrow(() -> this.tournament.inscribe(user));
    }

    @Test
    void inscribeInvalidState() {
        final var user = new User();
        user.setId(UUID.randomUUID());
        this.tournament.setId(UUID.randomUUID());
        this.tournament.setStartDate(LocalDate.now());
        this.tournament.setEndDate(LocalDate.now().plusDays(2));

        assertThrows(TournamentStateInvalidInscriptionRuntimeException.class, () -> this.tournament.inscribe(user));
    }

    @Test
    void inscribeDuplicate() {
        final var user = new User();
        user.setId(UUID.randomUUID());
        this.tournament.setId(UUID.randomUUID());
        this.tournament.setPlayers(List.of(user));
        this.tournament.setStartDate(LocalDate.now().plusDays(1));

        assertThrows(DuplicateEntityFoundRuntimeException.class, () -> this.tournament.inscribe(user));
    }

    @Test
    void validateAuthority() {
        final var user = new User();
        user.setId(UUID.randomUUID());
        this.tournament.setUserCreator(user);

        assertThrows(TournamentUnauthorizedUserActionRuntimeException.class, () -> this.tournament.validateAuthority(new WordleUser("username", "password", "some@email.com", UUID.randomUUID())));
    }

    @Test
    void listPositions() {
        final var tournamentLanguage = Language.ENGLISH;
        final var tournamentStartDate = LocalDate.now()
                                                          .plusDays(-5);

        final var firstMatchFirstUser = this.createMatch(tournamentStartDate, 1, tournamentLanguage);
        final var secondMatchFirstUser = this.createMatch(tournamentStartDate, 2, Language.SPANISH);
        final var thirdMatchFirstUser = this.createMatch(tournamentStartDate.plusDays(1), 3, tournamentLanguage);

        final var firstMatchSecondUser = this.createMatch(tournamentStartDate.plusDays(2), 1, tournamentLanguage);
        final var secondMatchSecondUser = this.createMatch(tournamentStartDate.plusDays(2), 2, Language.SPANISH);
        final var thirdMatchSecondUser = this.createMatch(tournamentStartDate.plusDays(3), 2, tournamentLanguage);

        final var firstMatchThirdUser = this.createMatch(tournamentStartDate.plusDays(4), 1, tournamentLanguage);
        final var secondMatchThirdUser = this.createMatch(tournamentStartDate.plusDays(4), 2, Language.SPANISH);
        final var thirdMatchThirdUser = this.createMatch(tournamentStartDate.plusDays(5), 1, tournamentLanguage);


        final var firstUser = new User();
        firstUser.setMatches(Arrays.asList(firstMatchFirstUser, secondMatchFirstUser, thirdMatchFirstUser));

        final var secondUser = new User();
        secondUser.setMatches(Arrays.asList(firstMatchSecondUser, secondMatchSecondUser, thirdMatchSecondUser));

        final var thirdUser = new User();
        thirdUser.setMatches(Arrays.asList(firstMatchThirdUser, secondMatchThirdUser, thirdMatchThirdUser));

        this.tournament.setStartDate(tournamentStartDate);
        this.tournament.setEndDate(tournamentStartDate.plusDays(5));
        this.tournament.setLanguage(tournamentLanguage);
        this.tournament.setPlayers(Arrays.asList(firstUser, secondUser, thirdUser));

        final var positions = this.tournament.listPositions().toList();
        assertEquals(firstUser.getName(), positions.get(0).getName());
        assertEquals(32, positions.get(0).getGuessesCount());
        assertEquals(secondUser.getName(), positions.get(1).getName());
        assertEquals(31, positions.get(1).getGuessesCount());
        assertEquals(thirdUser.getName(), positions.get(2).getName());
        assertEquals(30, positions.get(2).getGuessesCount());
    }

    private Match createMatch(LocalDate localDate, int guessesCount, Language language) {
        final var match = new Match();
        match.setDate(localDate);
        match.setGuessesCount(guessesCount);
        match.setLanguage(language);

        return match;
    }
}