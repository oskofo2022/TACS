import {UrlConstants} from "../constants/UrlConstants";
import {PostRequest} from "./PostRequest";

export class LoginRequest extends PostRequest{

    constructor(body) {
        super({url: UrlConstants.LOGIN, body: body})
    }

    static from(body) {
        return new LoginRequest(body);
    }

}