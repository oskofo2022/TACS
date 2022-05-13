import {QueryParams} from "../httpUtils/QueryParams";
import {UrlConstants} from "../constants/UrlConstants";
import {TournamentsRequest} from "./TournamentsRequest";
import {RequestBuilder} from "../httpUtils/RequestBuilder";
import {TournamentsResponse} from "../response/TournamentsResponse";

export class InscriptionsRequest {
    getURL = UrlConstants.INSCRIPTIONS;
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
        return new InscriptionsRequest(params);
    }

    async fetch(){
        this.response = await this.request.fetch();
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