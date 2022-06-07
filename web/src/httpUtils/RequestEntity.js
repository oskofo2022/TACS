import {QueryParams} from "./QueryParams";
import {HttpMethod} from "./RequestOptions";

export class RequestEntity {
    url: string;
    requestOptions: HttpMethod;
    queryParams: QueryParams;
    completedURL: string;
    pathParams: [{name: string, value: string}];
    header: string

    constructor({
                    url,
                    requestOptions,
                    pathParams = [],
                    queryParams
                }) {
        this.url = url;
        this.requestOptions = requestOptions;
        this.pathParams = pathParams;
        this.queryParams = queryParams;
        this.completedURL = this.completeURL();
    }

    completeURL(){
        let url = this.url;
        for(let p of this.pathParams) url = url.replace('{'+p.name+'}', p.value);
        if(!!this.queryParams)
            return url+'?'+this.queryParams.toString();
        return url;
    }

    async fetch(){
        return await fetch(this.completedURL, this.requestOptions);
    }

}