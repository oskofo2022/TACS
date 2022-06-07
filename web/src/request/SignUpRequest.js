import {UrlConstants} from "../constants/UrlConstants";
import {PostRequest} from "./PostRequest";

export class SignUpRequest extends PostRequest{

    constructor(body) {
        super({url: UrlConstants.USERS, body: body})
    }

    static from(body) {
        return new SignUpRequest(body);
    }
    
}