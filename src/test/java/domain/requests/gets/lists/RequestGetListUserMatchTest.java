package domain.requests.gets.lists;

import domain.persistence.entities.enums.Language;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RequestGetListUserMatchTest extends RequestGetPagedListTest<RequestGetListUserMatch> {
    @Test
    public void sortByDate() {
        this.request.setSortBy("date");

        this.valid();
    }

    @Test
    public void sortByLanguage() {
        this.request.setSortBy("language");

        this.valid();
    }

    @Test
    public void sortByInvalidValue() {
        this.request.setSortBy("invalid");

        this.invalid("", "RegexSortBy");
    }

    @Test
    public void getFilterName() {
        final var userId = UUID.randomUUID();
        this.request.setUserId(userId);

        final var filterValue = "user.id==%s".formatted(userId);

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

    @Test
    public void getFilterBottomDate() {
        this.request.setBottomDate(LocalDate.of(2022, 12, 22));
        final var filterValue = "date>=2022-12-22";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterTopDate() {
        this.request.setTopDate(LocalDate.of(2022, 12, 22));
        final var filterValue = "date<2022-12-23";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterBottomGuessesCount() {
        this.request.setBottomGuessesCount(2);
        final var filterValue = "guessesCount>=2";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    public void getFilterTopGuessesCount() {
        this.request.setTopGuessesCount(5);
        final var filterValue = "guessesCount<=5";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Override
    protected RequestGetListUserMatch createValidRequest() {
        return new RequestGetListUserMatch();
    }

    @Test
    @Override
    public void getDefaultFilter() {
        this.request = this.createValidRequest();
        assertEquals(Optional.empty(), this.request.getFilter());
    }

    @Test
    @Override
    public void defaultSortBy() {
        assertEquals("date", this.request.defaultSortBy());
    }
}