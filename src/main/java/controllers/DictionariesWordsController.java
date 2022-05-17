package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.integrations.APIs.dictionaries.DictionaryWordResponse;
import domain.responses.gets.lists.ResponseGetDictionaryWord;
import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public abstract class DictionariesWordsController {

    @GetMapping(path = UriConstants.Dictionaries.Word, produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetDictionaryWord> list(@PathVariable String word) throws IOException {
        final var dictionaryWordResponse = this.getWordResponse(word);

        final var responsesGetDictionaryWordMeaning = this.listMeanings(dictionaryWordResponse);

        final var responseGetDictionaryWord = new ResponseGetDictionaryWord(responsesGetDictionaryWordMeaning);
        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.DAYS)
                                                .cachePublic()
                                                .noTransform()
                                                .mustRevalidate();

        return ResponseEntity.ok()
                             .cacheControl(cacheControl)
                             .body(responseGetDictionaryWord);
    }

    private <TDictionaryWordResponse extends DictionaryWordResponse> List<ResponseGetDictionaryWordMeaning> listMeanings(TDictionaryWordResponse dictionaryWordResponse) {
        return dictionaryWordResponse == null ? new ArrayList<>() : dictionaryWordResponse.listMeanings();
    }

    abstract protected DictionaryWordResponse getWordResponse(String word);

    protected <TResponse> TResponse tryGetResponse(Call<TResponse> call) {
        try {
            return call.execute()
                       .body();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
