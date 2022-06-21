package domain.requests.common.gets.lists;

import constants.PaginationConstants;
import domain.requests.gets.lists.SortOrder;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

public abstract class RequestCommonGetPagedList {
    public RequestCommonGetPagedList() {
        this.page = PaginationConstants.Default.PAGE;
        this.pageSize = PaginationConstants.Default.PAGE_SIZE;
    }

    @Min(1)
    @Value("1")
    private Integer page;

    @Min(2)
    @Max(1000)
    @Value("100")
    private Integer pageSize;

    private String sortBy;

    private SortOrder sortOrder;

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page - 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return Optional.ofNullable(this.sortBy)
                       .filter(sb -> !sb.isBlank())
                       .orElseGet(this::defaultSortBy);
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortOrder getSortOrder() {
        return Optional.ofNullable(sortOrder)
                       .orElse(SortOrder.ASCENDING);
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    abstract protected String defaultSortBy();
}
