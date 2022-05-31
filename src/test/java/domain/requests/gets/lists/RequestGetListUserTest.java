package domain.requests.gets.lists;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class RequestGetListUserTest extends RequestGetPagedListTest<RequestGetListUser> {

    @Test
    public void sortByName() {
        this.request.setSortBy("name");

        this.valid();
    }

    @Test
    public void sortByEmail() {
        this.request.setSortBy("email");

        this.valid();
    }

    @Test
    public void sortByInvalidValue() {
        this.request.setSortBy("invalid");

        this.invalid("", "RegexSortBy");
    }

    @Test
    void getFilterName() {
        this.request.setName("a name");

        final var filterValue = "name=like='a name'";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    void getFilterEmail() {
        this.request.setEmail("some@email.com");

        final var filterValue = "email=like='some@email.com'";

        final var filter = this.request.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    @Override
    public void defaultSortBy() {
        assertEquals("name", this.request.defaultSortBy());
    }

    @Override
    protected RequestGetListUser createValidRequest() {
        return new RequestGetListUser();
    }

    @Test
    @Override
    public void getDefaultFilter() {
        this.request = this.createValidRequest();
        assertEquals(Optional.empty(), this.request.getFilter());
    }
}