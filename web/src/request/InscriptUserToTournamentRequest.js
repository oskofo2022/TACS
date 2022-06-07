import {UrlConstants} from "../constants/UrlConstants";
import {PostRequest} from "./PostRequest";

export class InscriptUserToTournamentRequest extends PostRequest {

    constructor(pathParams, body) {
        super({url: UrlConstants.TOURNAMENTS_USER_INSCRIPTION, body: body, pathParams: pathParams})
    }

    static from(pathParams, body) {
        return new InscriptUserToTournamentRequest(pathParams,body);
    }
}
