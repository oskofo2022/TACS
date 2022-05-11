import {EnglishDictRequest, SpanishDictRequest} from "../request/DictionaryRequest";

export const LanguagesConstants = Object.freeze({
    SPANISH: Object.freeze({
        label: 'Spanish',
        request: SpanishDictRequest,
        getRequest: (pathParams) => this.request.from(pathParams)
    }),
    ENGLISH: Object.freeze({
        label: 'English',
        request: EnglishDictRequest,
        getRequest: (pathParams) => this.request.from(pathParams)
    })
})