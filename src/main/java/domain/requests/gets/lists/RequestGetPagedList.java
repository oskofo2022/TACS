package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import constants.PaginationConstants;
import constants.RSQLConstants;
import domain.requests.common.gets.lists.RequestCommonGetPagedList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class RequestGetPagedList extends RequestCommonGetPagedList {
    public RequestGetPagedList() {
        this.restrictions = new ArrayList<>();
    }

    @JsonIgnore
    private final ArrayList<String> restrictions;

    @JsonIgnore
    public Optional<String> getFilter() {
        this.addRestrictions();
        return restrictions.stream()
                           .reduce(((concatenatedQueries, query) -> concatenatedQueries + RSQLConstants.AND + query));
    }

    @JsonIgnore
    public PageRequest getPageRequest() {
        return PageRequest.of(this.getPage(), this.getPageSize(), this.getSort());
    }

    @JsonIgnore
    private Sort getSort() {
        return Sort.by(this.getSortOrder() == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, this.getSortBy());
    }

    protected void addRestriction(String query, Object value) {
        this.addRestriction(query, value, Objects::nonNull);
    }

    protected void addRestriction(String query, String value) {
        this.addRestriction(query, value, v -> value != null && !value.isBlank());
    }

    private <TObject> void addRestriction(String query, TObject value, Function<TObject, Boolean> isValid) {
        if (isValid.apply(value)) {
            this.restrictions.add(query.formatted(value));
        }
    }

    abstract protected void addRestrictions();

    abstract protected String defaultSortBy();
}
