package controllers;

import constants.UriConstants;
import domain.integrations.APIs.dictionaries.DictionaryWordResponse;
import domain.integrations.APIs.dictionaries.DictionaryWordResponseAdapter;
import domain.integrations.APIs.dictionaries.english.EnglishDictionaryAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = UriConstants.Dictionaries.English.Words.URL)
public class EnglishDictionaryWordsController extends DictionariesWordsController {

    private final EnglishDictionaryAPI englishDictionaryAPI;
    private final DictionaryWordResponseAdapter dictionaryWordResponseAdapter;

    @Autowired
    public EnglishDictionaryWordsController(EnglishDictionaryAPI englishDictionaryAPI, DictionaryWordResponseAdapter dictionaryWordResponseAdapter) {
        this.englishDictionaryAPI = englishDictionaryAPI;
        this.dictionaryWordResponseAdapter = dictionaryWordResponseAdapter;
    }

    @Override
    protected DictionaryWordResponse getWordResponse(String word) {
        return this.dictionaryWordResponseAdapter.getWordResponse(this.tryGetResponse(this.englishDictionaryAPI.get(word)));
    }
}
