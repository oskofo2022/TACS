import {UrlConstants} from "../constants/UrlConstants";
import {RequestBuilder} from "../httpUtils/RequestBuilder";
import {QueryParams} from "../httpUtils/QueryParams";
import {UnauthorizedException} from "../errors/UnauthorizedException";
import {TournamentsResponse} from "../response/TournamentsResponse";

export class MyTournamentsRequest {
    getURL = UrlConstants.MYSELF_TOURNAMENTS;
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
        return new MyTournamentsRequest(params);
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
        return new TournamentsResponse(response);
    }
}