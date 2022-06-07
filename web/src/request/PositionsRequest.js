import {QueryParams} from "../httpUtils/QueryParams";
import {UrlConstants} from "../constants/UrlConstants";
import {TournamentsResponse} from "../response/TournamentsResponse";
import {PositionsResponse} from "../response/PositionsResponse";
import {GetRequest} from "./GetRequest";

export class PositionsRequest extends GetRequest{

    constructor(queryParams: QueryParams) {
        super({url: UrlConstants.POSITIONS, queryParams: queryParams})
    }

    static from(params: QueryParams) {
        return new PositionsRequest(params);
    }

    async fetchAsPaged(): Promise<TournamentsResponse>{
        const response = await this.fetchAsJSON();
        return new PositionsResponse(response);
    }
}