export class Params {
    constructor(
                page=0,
                pageSize=5,
                sortBy='',
                sortOrder='ASC'
            ){
        this.page=page
        this.pageSize=pageSize
        this.sortBy=sortBy
        this.sortOrder=sortOrder
    }
    toString(){
        let p = [];
        for (const key in this) p.push(key+'='+this[key]);
        return p.join('&');
    }
}

