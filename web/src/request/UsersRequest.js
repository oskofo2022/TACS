import {QueryParams} from "../httpUtils/QueryParams";
import {UrlConstants} from "../constants/UrlConstants";
import {GetRequest} from "./GetRequest";
import {UsersResponse} from "response/UsersResponse";

export class UsersRequest extends GetRequest{

    constructor(queryParams: QueryParams) {
        super({url: UrlConstants.USERS, queryParams: queryParams})
    }

    static from(params: QueryParams) {        
        return new UsersRequest(params);
    }

    async fetchAsPaged() {
        const response = await this.fetchAsJSON();
        return new UsersResponse(response);
    }

}