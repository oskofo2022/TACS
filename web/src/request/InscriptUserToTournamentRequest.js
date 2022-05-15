import {UrlConstants} from "../constants/UrlConstants";
import {RequestBuilder} from "../httpUtils/RequestBuilder";

export class InscriptUserToTournamentRequest{
    postURL = UrlConstants.TOURNAMENTS_USER_INSCRIPTION;
    request;
    response;
    body;

    buildRequest = () => {
        let rb = RequestBuilder.post(this.postURL, this.body);
        this.request = rb.build();
    }

    constructor(body) {
        this.body = body;
        this.buildRequest();
    }

    static from(body) {
        return new InscriptUserToTournamentRequest(body);
    }

    async fetch(){
        this.response = await this.request.fetch();
        return this.response;
    }

    async fetchAsJSON(){
        await this.fetch()
        return this.response.json();
    }
}
