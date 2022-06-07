import {UrlConstants} from "../constants/UrlConstants";
import {TournamentsResponse} from "../response/TournamentsResponse";
import { GetRequest } from "./GetRequest";

export class MyTournamentsRequest extends GetRequest {

    constructor(queryParams) {
        super({url: UrlConstants.MYSELF_TOURNAMENTS, queryParams: queryParams})
    }

    static from(queryParams) {
        return new MyTournamentsRequest(queryParams);
    }

    async fetchAsPaged(): Promise<TournamentsResponse>{
        const response = await this.fetchAsJSON();
        return new TournamentsResponse(response);
    }
}