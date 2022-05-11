export class PagedResponse{
    pageCount;
    pageItems;
    totalCount;

    constructor({
                    pageCount: pageCount,
                    pageItems: pageItems,
                    totalCount: totalCount
            }) {
        this.pageCount = pageCount;
        this.pageItems = pageItems;
        this.totalCount = totalCount;
    }
}