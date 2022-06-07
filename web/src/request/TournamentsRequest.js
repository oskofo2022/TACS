import {QueryParams} from "../httpUtils/QueryParams";
import {UrlConstants} from "../constants/UrlConstants";
import {TournamentsResponse} from "../response/TournamentsResponse";
import { GetRequest } from "./GetRequest";

export class TournamentsRequest extends GetRequest{

    constructor(queryParams: QueryParams) {
        super({url: UrlConstants.PUBLIC_TOURNAMENTS, queryParams: queryParams})
    }

    static from(params: QueryParams) {
        return new TournamentsRequest(params);
    }

    async fetchAsPaged(): Promise<TournamentsResponse>{
        const response = await this.fetchAsJSON();
        return new TournamentsResponse(response);
    }

}