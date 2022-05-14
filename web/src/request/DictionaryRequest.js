import {UrlConstants} from "../constants/UrlConstants";
import {RequestBuilder} from "../httpUtils/RequestBuilder";
import {DictionaryResponse} from "../response/DictionaryResponse";
import {MeaningNotFoundException} from "../errors/MeaningNotFoundException";

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

    async fetchMeanings(): Promise<DictionaryResponse>{
        const response = await this.fetchAsJSON();
        if(!response.meanings || !response.meanings.length) throw new MeaningNotFoundException('NO MEANINGS EXCEPTION');
        return new DictionaryResponse(response.meanings);
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