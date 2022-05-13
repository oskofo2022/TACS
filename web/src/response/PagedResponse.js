export class PagedResponse{
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