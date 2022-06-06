package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.requests.common.gets.lists.RequestCommonGetPagedList;
import domain.responses.gets.lists.ResponseGetPagedList;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class RequestGetListOnMemoryPagedList<T> extends RequestCommonGetPagedList {
    @JsonIgnore
    private long getElementsToSkip() {
        return (long)this.getPage() * this.getPageSize();
    }

    @JsonIgnore
    public <TResponseGetList> ResponseGetPagedList<TResponseGetList> paginate(Supplier<Stream<T>> streamSupplier, Function<T, TResponseGetList> mapping) {
        final var totalCount = streamSupplier.get()
                                                   .filter(this::isValid)
                                                   .count();
        final var pageCount = ((totalCount - 1) / this.getPageSize()) + 1;

        final var comparator = this.getComparatorMap()
                                                 .get(this.getSortBy());

        final var sortOrder = this.getSortOrder();

        final var pageElements = streamSupplier.get()
                                                                 .filter(this::isValid)
                                                                 .sorted(sortOrder.getComparator(comparator))
                                                                 .skip(this.getElementsToSkip())
                                                                 .limit(this.getPageSize())
                                                                 .map(mapping)
                                                                 .toList();
        return new ResponseGetPagedList<>(pageCount, pageElements, totalCount);
    }

    @JsonIgnore
    public ResponseGetPagedList<T> paginate(Supplier<Stream<T>> streamSupplier) {
        return this.paginate(streamSupplier, Function.identity());
    }

    public abstract boolean isValid(T instance);

    public abstract Map<String, Comparator<T>> getComparatorMap();
}
