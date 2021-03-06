import {UrlConstants} from "../constants/UrlConstants";
import {InscriptionsResponse} from "../response/InscriptionsResponse";
import {GetRequest} from "./GetRequest";

export class InscriptionsRequest extends GetRequest  {

    constructor(queryParams) {
        super({url: UrlConstants.INSCRIPTIONS, queryParams: queryParams})
    }

    static from(queryParams) {
        return new InscriptionsRequest(queryParams);
    }

    async fetchAsPaged() {
        const response = await this.fetchAsJSON();
        return new InscriptionsResponse(response);
    }

}