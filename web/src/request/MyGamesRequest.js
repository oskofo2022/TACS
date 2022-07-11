import { MyGamesResponse } from "response/MyGamesResponse";
import {UrlConstants} from "../constants/UrlConstants";
import {GetRequest} from "./GetRequest";

export class MyGamesRequest extends GetRequest  {

    constructor(queryParams) {
        super({url: UrlConstants.MYSELF_MATCHES, queryParams: queryParams})
    }

    static from(queryParams) {
        return new MyGamesRequest(queryParams);
    }

    async fetchAsPaged() {
        const response = await this.fetchAsJSON();
        return new MyGamesResponse(response);
    }

}