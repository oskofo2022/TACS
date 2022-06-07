import {RequestEntityBuilder} from "../httpUtils/RequestEntityBuilder";
import { AbstractRequest } from "./AbstractRequest";

export class GetRequest extends AbstractRequest{

    buildRequest = (queryParams) => {
        let rb = RequestEntityBuilder.get(this.url);
        this.request = rb.setQueryParams(queryParams)
            .setPathParms(this.pathParams)
            .build();
    }

    constructor({url: url, queryParams: queryParams, pathParams = {}}) {
        super({url: url, pathParams: pathParams})
        this.buildRequest(queryParams);
    }

}