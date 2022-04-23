package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import constants.PaginationConstants;
import constants.RSQLConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class RequestGetPagedList {
    public RequestGetPagedList() {
        this.page = PaginationConstants.Default.PAGE;
        this.pageSize = PaginationConstants.Default.PAGE_SIZE;
        this.restrictions = new ArrayList<>();
    }

    @Min(1)
    private Integer page;

    @Min(2)
    @Max(1000)
    private Integer pageSize;

    @NotBlank
    private String sortBy;

    private SortOrder sortOrder;

    public int getPage() {
        return page;
    }

    @JsonIgnore
    private final ArrayList<String> restrictions;

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }


    @JsonIgnore
    public PageRequest getPageRequest() {
        return PageRequest.of(this.page - 1, this.pageSize, this.getSort());
    }

    @JsonIgnore
    private Sort getSort() {
        return Sort.by(this.sortOrder == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, this.sortBy);
    }

    @JsonIgnore
    public Optional<String> getFilter() {
        this.addRestrictions();
        return restrictions.stream()
                           .reduce(((concatenatedQueries, query) -> concatenatedQueries + RSQLConstants.AND + query));
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

    protected void addRestrictions() {

    }
}
