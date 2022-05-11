import {QueryParams} from "../httpUtils/QueryParams";
import {RequestBuilder} from "../httpUtils/RequestBuilder";
import {PagedResponse} from "../response/PagedResponse";
import {UrlConstants} from "../constants/UrlConstants";

export class TournamentsRequest {
    getURL = UrlConstants.PUBLIC_TOURNAMENTS;
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
        return new TournamentsRequest(params);
    }

    async fetch(){
        return await this.request.fetch();
    }

    async fetchAsJSON(){
        let response = await this.fetch();
        return response.json();
    }

    fetchAsPaged(){
        return this.fetchAsJSON().then(r => new PagedResponse(r));
    }

}