package controllers;

import constants.UriConstants;
import domain.integrations.APIs.dictionaries.english.EnglishDictionaryAPI;
import domain.integrations.APIs.dictionaries.english.EnglishDictionaryWordResponse;
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
    protected List<ResponseGetDictionaryWordMeaning> getMeanings(String word) {
        final var call = this.englishDictionaryAPI.get(word);
        List<EnglishDictionaryWordResponse> englishDictionaryWordResponses;
        try {
            final var response = call.execute();
            englishDictionaryWordResponses = response.body();

            if (englishDictionaryWordResponses == null) {
                englishDictionaryWordResponses = new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return englishDictionaryWordResponses.stream()
                                             .findFirst()
                                             .map(this::map)
                                             .orElse(new ArrayList<>());
    }

    private List<ResponseGetDictionaryWordMeaning> map(EnglishDictionaryWordResponse englishDictionaryWordResponse) {
        final var englishDictionaryWordMeanings = englishDictionaryWordResponse.getMeanings();

        return englishDictionaryWordMeanings.stream()
                                            .map(edwm -> new ResponseGetDictionaryWordMeaning(edwm.getPartOfSpeech(), edwm.getDefinitions().get(0).getDefinition()))
                                            .toList();
    }
}
