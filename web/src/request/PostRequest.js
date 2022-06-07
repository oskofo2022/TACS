import {RequestEntityBuilder} from "../httpUtils/RequestEntityBuilder";
import { AbstractRequest } from "./AbstractRequest";

export class PostRequest extends AbstractRequest{

    buildRequest = () => {
        let rb = RequestEntityBuilder.post(this.url, this.body);
        this.request = rb.setPathParms(this.pathParams).build();
    }

    constructor({url, body, pathParams}) {
        super({url, body, pathParams})
        this.buildRequest();
    }

}