import { UrlConstants } from "constants/UrlConstants";
import { GetRequest } from "./GetRequest";


export class HelpRequest extends GetRequest{

    constructor(pathParams, queryParams) {
        super({pathParams, url: UrlConstants.HELPER, queryParams})
    }

    static from(pathParams, queryParams) {
        return new HelpRequest(pathParams, queryParams);
    }
    

}