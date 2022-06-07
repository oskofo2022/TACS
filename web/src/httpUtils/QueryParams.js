export class QueryParams {
    constructor({
                    page = 1,
                    pageSize = 1000,
                    sortBy = 'id',
                    sortOrder = 'ASCENDING',
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
        for (const key in this) {
            let value;
            if(Array.isArray(this[key]))
                value = this[key].map(k => key+'='+k).join('&');
            else
                value = key+'='+this[key];
            p.push(value);
        }
        return p.join('&');
    }
}

