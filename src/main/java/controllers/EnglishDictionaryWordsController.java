package controllers;

import constants.UriConstants;
import domain.integrations.APIs.dictionaries.DictionaryWordResponse;
import domain.integrations.APIs.dictionaries.english.EnglishDictionaryAPI;
import domain.integrations.APIs.dictionaries.english.EnglishDictionaryWordResponse;
import domain.integrations.APIs.dictionaries.english.EnglishDictionaryWordResponseWrapper;
import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = UriConstants.Dictionaries.English.Words.URL)
public class EnglishDictionaryWordsController extends DictionariesWordsController {

    private final EnglishDictionaryAPI englishDictionaryAPI;

    @Autowired
    public EnglishDictionaryWordsController(EnglishDictionaryAPI englishDictionaryAPI) {
        this.englishDictionaryAPI = englishDictionaryAPI;
    }

    @Override
    protected DictionaryWordResponse getWordResponse(String word) throws IOException {
        return new EnglishDictionaryWordResponseWrapper(this.tryGetResponse(this.englishDictionaryAPI.get(word)));
    }
}
