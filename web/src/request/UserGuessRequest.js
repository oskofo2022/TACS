import {UrlConstants} from "../constants/UrlConstants";
import { PostRequest } from "./PostRequest";

export class UserGuessRequest extends PostRequest{
    
    constructor(body) {
        super({url: UrlConstants.USER_GUESS, body: body})
    }

    static from(body) {
        return new UserGuessRequest(body);
    }

}

