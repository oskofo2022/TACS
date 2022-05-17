import {UrlConstants} from "../constants/UrlConstants";
import {RequestBuilder} from "../httpUtils/RequestBuilder";
import {UnauthorizedException} from "../errors/UnauthorizedException";

export class UserGuessRequest {
    postURL = UrlConstants.USER_GUESS;
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
        return new UserGuessRequest(body);
    }

    async fetch(){
        this.response = await this.request.fetch();
        if(this.response.status === 401) throw new UnauthorizedException('Unauthorized');
        return this.response;
    }

}

