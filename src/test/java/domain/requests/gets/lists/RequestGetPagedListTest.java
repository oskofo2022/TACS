package domain.requests.gets.lists;

import constants.SuppressWarningsConstants;
import domain.requests.common.gets.lists.RequestCommonGetPagedListTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings(SuppressWarningsConstants.ALL)
public abstract class RequestGetPagedListTest<TRequestGetPagedList extends RequestGetPagedList> extends RequestCommonGetPagedListTest<TRequestGetPagedList> {
    @Test
    void getPageRequest() {
        final var sortBy = "column";

        this.request.setPage(2);
        this.request.setPageSize(200);
        this.request.setSortBy(sortBy);
        this.request.setSortOrder(SortOrder.DESCENDING);

        final var pageRequest = this.request.getPageRequest();

        final var sortOrder = pageRequest.getSort().getOrderFor(sortBy);

        assertEquals(1, pageRequest.getPageNumber());
        assertEquals(200, pageRequest.getPageSize());
        assertNotNull(sortOrder);
        assertEquals(Sort.Direction.DESC, sortOrder.getDirection());
    }

    public abstract void getDefaultFilter();
}