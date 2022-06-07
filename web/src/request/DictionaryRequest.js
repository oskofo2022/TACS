import {UrlConstants} from "../constants/UrlConstants";
import {DictionaryResponse} from "../response/DictionaryResponse";
import {MeaningNotFoundException} from "../errors/MeaningNotFoundException";
import { GetRequest } from "./GetRequest";

class DictionaryRequest extends GetRequest{

    constructor(pathParams, url) {
        super({pathParams, url})
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