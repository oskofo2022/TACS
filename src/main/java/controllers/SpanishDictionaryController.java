package controllers;

import constants.UriConstants;
import domain.integrations.APIs.dictionaries.DictionaryWordResponse;
import domain.integrations.APIs.dictionaries.spanish.SpanishDictionaryAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = UriConstants.Dictionaries.Spanish.Words.URL)
public class SpanishDictionaryController extends DictionariesWordsController {
    private final SpanishDictionaryAPI spanishDictionaryAPI;

    @Autowired
    public SpanishDictionaryController(SpanishDictionaryAPI spanishDictionaryAPI) {
        this.spanishDictionaryAPI = spanishDictionaryAPI;
    }

    @Override
    protected DictionaryWordResponse getWordResponse(String word) {
        return this.tryGetResponse(this.spanishDictionaryAPI.get(word));
    }
}
