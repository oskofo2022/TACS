import {EnglishDictRequest, SpanishDictRequest} from "../request/DictionaryRequest";

class Language {
    constructor(label, request){
        this.label = label
        this.request = request
    }
    getRequest = (pathParams) => this.request.from(pathParams)
}

export const LanguagesConstants = Object.freeze({
    SPANISH: Object.freeze(new Language('Spanish', SpanishDictRequest)),
    ENGLISH: Object.freeze(new Language('English', EnglishDictRequest)),
})