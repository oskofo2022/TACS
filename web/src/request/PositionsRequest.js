import {QueryParams} from "../httpUtils/QueryParams";
import {UrlConstants} from "../constants/UrlConstants";
import {GetRequest} from "./GetRequest";
import { PositionsResponse } from "response/PositionsResponse";

export class PositionsRequest extends GetRequest{

    constructor(pathParams, queryParams: QueryParams) {
        super({pathParams: pathParams, url: UrlConstants.POSITIONS, queryParams: queryParams})
    }

    static from(pathParams, queryParams: QueryParams) {
        return new PositionsRequest(pathParams, queryParams);
    }

    async fetchAsPaged(){
        const response = await this.fetchAsJSON();
        return new PositionsResponse(response);
    }
}