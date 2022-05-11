package controllers;

import constants.UriConstants;
import domain.integrations.APIs.dictionaries.DictionaryWordResponse;
import domain.integrations.APIs.dictionaries.english.EnglishDictionaryAPI;
import domain.integrations.APIs.dictionaries.DictionaryWordResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = UriConstants.Dictionaries.English.Words.URL)
public class EnglishDictionaryWordsController extends DictionariesWordsController {

    private final EnglishDictionaryAPI englishDictionaryAPI;

    @Autowired
    public EnglishDictionaryWordsController(EnglishDictionaryAPI englishDictionaryAPI) {
        this.englishDictionaryAPI = englishDictionaryAPI;
    }

    @Override
    protected DictionaryWordResponse getWordResponse(String word) {
        return new DictionaryWordResponseWrapper(this.tryGetResponse(this.englishDictionaryAPI.get(word)));
    }
}
