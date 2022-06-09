import {UrlConstants} from "../constants/UrlConstants";
import { PostRequest } from "./PostRequest";

export class InscriptMyselfRequest extends PostRequest {

    constructor(pathParams) {
        super({url: UrlConstants.PUBLIC_TOURNAMENTS_MYSELF_INSCRIPTION, pathParams: pathParams, body: undefined})
    }

    static from(pathParams) {
        return new InscriptMyselfRequest(pathParams);
    }
}