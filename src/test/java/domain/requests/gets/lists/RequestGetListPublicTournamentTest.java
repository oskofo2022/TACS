package domain.requests.gets.lists;

import constants.SuppressWarningsConstants;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.requests.common.gets.lists.RequestCommonGetListTournamentTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings(SuppressWarningsConstants.ALL)
class RequestGetListPublicTournamentTest extends RequestCommonGetListTournamentTest<RequestGetListPublicTournament> {
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
    public void sortByInvalidValue() {
        this.request.setSortBy("invalid");

        this.invalid("", "RegexSortBy");
    }

    @Test
    public void getFilterName() {
        this.request.setName("a name");

        final var filterValue = "name=like='a name';visibility==PUBLIC";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterId() {
        final var id = UUID.randomUUID();
        this.request.setId(id);
        final var filterValue = "id==%s;visibility==PUBLIC".formatted(id);

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterTournamentBottomEndDate() {
        this.request.setTournamentBottomEndDate(LocalDate.of(2022, 12, 22));
        final var filterValue = "endDate>=2022-12-22;visibility==PUBLIC";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterTournamentTopEndDate() {
        this.request.setTournamentTopEndDate(LocalDate.of(2022, 12, 22));
        final var filterValue = "endDate<2022-12-23;visibility==PUBLIC";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterTournamentBottomStartDate() {
        this.request.setTournamentBottomStartDate(LocalDate.of(2022, 12, 22));
        final var filterValue = "startDate>=2022-12-22;visibility==PUBLIC";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterTournamentTopStartDate() {
        this.request.setTournamentTopStartDate(LocalDate.of(2022, 12, 22));
        final var filterValue = "startDate<2022-12-23;visibility==PUBLIC";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterState() {
        this.request.setState(TournamentState.STARTED);
        final var filterValue = "state==STARTED;visibility==PUBLIC";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterLanguage() {
        this.request.setLanguage(Language.SPANISH);
        final var filterValue = "language==SPANISH;visibility==PUBLIC";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Override
    protected RequestGetListPublicTournament createValidRequest() {
        return new RequestGetListPublicTournament();
    }

    @Test
    @Override
    public void getDefaultFilter() {
        this.request = this.createValidRequest();
        assertEquals("visibility==PUBLIC", this.request.getFilter().get());
    }
}