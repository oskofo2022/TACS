import {UrlConstants} from "../constants/UrlConstants";
import {PostRequest} from "./PostRequest";

export class SignUpRequest extends PostRequest{

    constructor(body) {
        super(UrlConstants.USERS, body)
    }

    static from(body) {
        return new SignUpRequest(body);
    }
    
}