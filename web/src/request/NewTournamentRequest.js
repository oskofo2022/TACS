import {UrlConstants} from "../constants/UrlConstants";
import {PostRequest} from "./PostRequest";

export class NewTournamentRequest extends PostRequest {

    constructor(body) {
        super({url: UrlConstants.TOURNAMENTS, body: body})
    }

    static from(body) {
        return new NewTournamentRequest(body);
    }    

}