package domain.requests.gets.lists;

import constants.SuppressWarningsConstants;
import domain.files.FileLinesStreamer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings(SuppressWarningsConstants.ALL)
abstract class RequestGetPagedListTest<TRequestGetPagedList extends RequestGetPagedList> {
    protected TRequestGetPagedList requestGetPagedList;

    abstract void setUp();


    @Test
    void getPageRequest() {
        final var sortBy = "column";

        this.requestGetPagedList.setPage(2);
        this.requestGetPagedList.setPageSize(200);
        this.requestGetPagedList.setSortBy("column");
        this.requestGetPagedList.setSortOrder(SortOrder.DESCENDING);

        final var pageRequest = this.requestGetPagedList.getPageRequest();

        final var sortOrder = pageRequest.getSort().getOrderFor("column");

        assertEquals(1, pageRequest.getPageNumber());
        assertEquals(200, pageRequest.getPageSize());
        assertNotNull(sortOrder);
        assertEquals(Sort.Direction.DESC, sortOrder.getDirection());
    }

    @Test
    void getDefaultFilter() {
        assertEquals(Optional.empty(), this.requestGetPagedList.getFilter());
    }

    abstract void getFilter();

    abstract void defaultSortBy();
}