package domain.requests.gets.lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RequestGetListUserTest extends RequestGetPagedListTest<RequestGetListUser> {

    @BeforeEach
    @Override
    void setUp() {
        this.requestGetPagedList = new RequestGetListUser();
    }

    @Test
    void getFilterName() {
        this.requestGetPagedList.setName("a name");

        final var filterValue = "name=like='a name'";

        final var filter = this.requestGetPagedList.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    void getFilterEmail() {
        this.requestGetPagedList.setEmail("some@email.com");

        final var filterValue = "email=like='some@email.com'";

        final var filter = this.requestGetPagedList.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    @Override
    void getFilter() {
        this.requestGetPagedList.setName("a name");
        this.requestGetPagedList.setEmail("some@email.com");

        final var filterValue = "email=like='some@email.com';name=like='a name'";

        final var filter = this.requestGetPagedList.getFilter();
        assertTrue(filter.isPresent());
        assertEquals(filterValue, filter.get());
    }

    @Test
    @Override
    void defaultSortBy() {
        assertEquals("name", this.requestGetPagedList.defaultSortBy());
    }
}