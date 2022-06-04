package domain.requests.gets.lists;

import domain.requests.common.gets.lists.RequestCommonGetPagedListTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract  class RequestGetListOnMemoryPagedListTest<TRequestGetListOnMemoryPagedList extends RequestGetListOnMemoryPagedList<?>> extends RequestCommonGetPagedListTest<TRequestGetListOnMemoryPagedList> {

    @Test
    void paginate() {
    }
}