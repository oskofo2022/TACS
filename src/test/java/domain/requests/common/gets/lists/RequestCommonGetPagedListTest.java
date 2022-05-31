package domain.requests.common.gets.lists;

import domain.requests.RequestAnnotationTest;
import org.junit.jupiter.api.Test;

public abstract class RequestCommonGetPagedListTest<TRequestGetPagedList extends RequestCommonGetPagedList> extends RequestAnnotationTest<TRequestGetPagedList> {
    @Test
    void pageBelowRange() {
        this.request.setPage(0);

        this.invalid("page", "Min");
    }

    @Test
    void pageSizeBelowRange() {
        this.request.setPageSize(1);

        this.invalid("pageSize", "Min");
    }

    @Test
    void pageSizeAboveRange() {
        this.request.setPageSize(1001);

        this.invalid("pageSize", "Max");
    }

    public abstract void defaultSortBy();
}
