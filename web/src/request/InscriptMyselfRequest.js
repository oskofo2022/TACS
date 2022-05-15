import {UrlConstants} from "../constants/UrlConstants";
import {RequestBuilder} from "../httpUtils/RequestBuilder";
import {UnauthorizedException} from "../errors/UnauthorizedException";

export class InscriptMyselfRequest {
    postURL = UrlConstants.PUBLIC_TOURNAMENTS_MYSELF_INSCRIPTION;
    request;
    response;
    body;

    buildRequest = () => {
        let rb = RequestBuilder.post(this.postURL, '');
        this.request = rb.setPathParms(this.pathParams).build();
    }

    constructor(pathParams) {
        this.pathParams = pathParams;
        this.buildRequest();
    }

    static from(pathParams) {
        return new InscriptMyselfRequest(pathParams);
    }

    async fetch(){
        this.response = await this.request.fetch();
        if(this.response.status === 401) throw new UnauthorizedException('Unauthorized');
        return this.response;
    }

    async fetchAsJSON(){
        await this.fetch()
        return this.response.json();
    }
}