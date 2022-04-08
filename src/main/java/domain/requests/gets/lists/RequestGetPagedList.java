package domain.requests.gets.lists;

import constants.PaginationConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public abstract class RequestGetPagedList {
    public RequestGetPagedList() {
        this.page = PaginationConstants.Default.PAGE;
        this.pageSize = PaginationConstants.Default.PAGE_SIZE;
    }

    @Size(min = 1)
    private int page;

    @Size(min = 2, max = 1000)
    private int pageSize;

    @NotBlank
    private String sortBy;

    private SortOrder sortOrder;

    public int getPage() {
        return page;
    }

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
}
