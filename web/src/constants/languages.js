import {EnglishDictRequest, SpanishDictRequest} from "../request/DictionaryRequest";

export const LANGUAGES = Object.freeze({
    SPANISH: Object.freeze({
        label: 'Spanish',
        request: SpanishDictRequest
    }),
    ENGLISH: Object.freeze({
        label: 'English',
        request: EnglishDictRequest
    })
})