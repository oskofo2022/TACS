package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.requests.common.gets.lists.RequestCommonGetPagedList;

import java.util.stream.Stream;

public abstract class RequestGetListOnMemoryPagedList<T> extends RequestCommonGetPagedList {
    @JsonIgnore
    private long getElementsToSkip() {
        return (long)this.getPage() * this.getPageSize();
    }

    @JsonIgnore
    public Stream<T> paginate(Stream<T> stream) {
        // TODO: Implement sorting and see to return ResponseGetPagedList
        return stream.skip(this.getElementsToSkip())
                     .limit(this.getPageSize());
    }
}
