import {QueryParams} from "../httpUtils/QueryParams";
import {UrlConstants} from "../constants/UrlConstants";
import {RequestBuilder} from "../httpUtils/RequestBuilder";
import {TournamentsResponse} from "../response/TournamentsResponse";
import {UnauthorizedException} from "../errors/UnauthorizedException";
import {PositionsResponse} from "../response/PositionsResponse";

export class PositionsRequest {
    getURL = UrlConstants.POSITIONS;
    response;
    request;

    buildRequest = (params) => {
        let rb = RequestBuilder.get(this.getURL);
        this.request = rb.setQueryParams(params)
            .build();
    }

    constructor(params: QueryParams) {
        this.buildRequest(params);
    }

    static from(params: QueryParams) {
        return new PositionsRequest(params);
    }

    async fetch(){
        this.response = await this.request.fetch();
        if(this.response.status === 401) throw new UnauthorizedException('Unauthorized');
        return this.response;
    }

    async fetchAsJSON(){
        const response = await this.fetch();
        return await response.json();
    }

    async fetchAsPaged(): Promise<TournamentsResponse>{
        const response = await this.fetchAsJSON();
        return new PositionsResponse(response);
    }
}