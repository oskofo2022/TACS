export class QueryParams {
    constructor({
                    page = 0,
                    pageSize = 5,
                    sortBy = 'id',
                    sortOrder = 'ASC',
                    ...params
                }){
        this.page=page;
        this.pageSize=pageSize;
        this.sortBy=sortBy;
        this.sortOrder=sortOrder;
        for (const [k,v] of Object.entries(params)) this.addParam({[k]: v});
    }

    addParam(keyValue){
        Object.assign(this, keyValue);
        return this;
    }

    removeParam(key: string){
        delete this[key];
        return this;
    }

    toString(){
        let p = [];
        for (const key in this) p.push(key+'='+this[key]);
        return p.join('&');
    }
}

