package domain.requests.gets.lists;

import domain.requests.common.gets.lists.RequestCommonGetPagedListTest;

public abstract class RequestGetListOnMemoryPagedListTest<T, TRequestGetListOnMemoryPagedList extends RequestGetListOnMemoryPagedList<T>> extends RequestCommonGetPagedListTest<TRequestGetListOnMemoryPagedList> {

    public abstract void paginate();
}