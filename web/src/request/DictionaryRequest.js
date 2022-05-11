import {UrlConstants} from "../constants/UrlConstants";
import {RequestBuilder} from "../httpUtils/RequestBuilder";

class DictionaryRequest{
    getURL;
    request;
    pathParams;
    response;

    buildRequest = () => {
        let rb = RequestBuilder.get(this.getURL);
        this.request = rb.setPathParms(this.pathParams).build();
    }

    constructor(pathParams, url) {
        this.pathParams = pathParams;
        this.getURL = url;
        this.buildRequest();
    }

    async fetch(){
        this.response = await this.request.fetch();
        return this.response;
    }

    async fetchAsJSON(){
        await this.fetch()
        return this.response.json();
    }

}

export class SpanishDictRequest extends DictionaryRequest{

    constructor(pathParams) {
        super(pathParams, UrlConstants.SPANISH_DICTIONARY);
    }

    static from(pathParams) {
        return new SpanishDictRequest(pathParams);
    }
}

export class EnglishDictRequest extends DictionaryRequest{

    constructor(pathParams) {
        super(pathParams, UrlConstants.ENGLISH_DICTIONARY);
    }

    static from(pathParams) {
        return new EnglishDictRequest(pathParams);
    }

}