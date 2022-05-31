package domain.requests.gets.lists;

import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.requests.common.gets.lists.RequestCommonGetListTournamentTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RequestGetListMyTournamentTest extends RequestCommonGetListTournamentTest<RequestGetListMyTournament> {
    @Test
    public void sortByName() {
        this.request.setSortBy("name");

        this.valid();
    }

    @Test
    public void sortByState() {
        this.request.setSortBy("state");

        this.valid();
    }

    @Test
    public void sortById() {
        this.request.setSortBy("id");

        this.valid();
    }

    @Test
    public void sortByStartDate() {
        this.request.setSortBy("startDate");

        this.valid();
    }

    @Test
    public void sortByEndDate() {
        this.request.setSortBy("endDate");

        this.valid();
    }

    @Test
    public void sortByVisibility() {
        this.request.setSortBy("visibility");

        this.valid();
    }

    @Test
    public void sortByInvalidValue() {
        this.request.setSortBy("invalid");

        this.invalid("", "RegexSortBy");
    }

    @Test
    public void getFilterUserCreatorId() {
        final var id = UUID.randomUUID();
        this.request.setUserCreatorId(id);
        final var filterValue = "userCreator.id==%s".formatted(id);

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterVisibility() {
        this.request.setVisibility(Visibility.PRIVATE);
        final var filterValue = "visibility==PRIVATE";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterName() {
        this.request.setName("a name");

        final var filterValue = "name=like='a name'";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterId() {
        final var id = UUID.randomUUID();
        this.request.setId(id);
        final var filterValue = "id==%s".formatted(id);

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterTournamentBottomEndDate() {
        this.request.setTournamentBottomEndDate(LocalDate.of(2022, 12, 22));
        final var filterValue = "endDate>=2022-12-22";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterTournamentTopEndDate() {
        this.request.setTournamentTopEndDate(LocalDate.of(2022, 12, 22));
        final var filterValue = "endDate<2022-12-23";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterTournamentBottomStartDate() {
        this.request.setTournamentBottomStartDate(LocalDate.of(2022, 12, 22));
        final var filterValue = "startDate>=2022-12-22";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterTournamentTopStartDate() {
        this.request.setTournamentTopStartDate(LocalDate.of(2022, 12, 22));
        final var filterValue = "startDate<2022-12-23";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterState() {
        this.request.setState(TournamentState.STARTED);
        final var filterValue = "state==STARTED";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterLanguage() {
        this.request.setLanguage(Language.SPANISH);
        final var filterValue = "language==SPANISH";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Override
    protected RequestGetListMyTournament createValidRequest() {
        return new RequestGetListMyTournament();
    }

    @Test
    @Override
    public void getDefaultFilter() {
        this.request = this.createValidRequest();
        assertEquals(Optional.empty(), this.request.getFilter());
    }
}