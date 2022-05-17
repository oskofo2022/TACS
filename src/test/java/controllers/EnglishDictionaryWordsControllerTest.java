package controllers;

import constants.SuppressWarningsConstants;
import domain.integrations.APIs.dictionaries.DictionaryWordResponse;
import domain.integrations.APIs.dictionaries.DictionaryWordResponseAdapter;
import domain.integrations.APIs.dictionaries.english.EnglishDictionaryAPI;
import domain.integrations.APIs.dictionaries.english.entities.EnglishDictionaryWordResponse;
import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
class EnglishDictionaryWordsControllerTest {

    @Mock
    private EnglishDictionaryAPI englishDictionaryAPI;
    @Mock
    private DictionaryWordResponseAdapter dictionaryWordResponseAdapter;

    @InjectMocks
    private EnglishDictionaryWordsController englishDictionaryWordsController;

    @Test
    void getWordResponse() throws Exception {
        final var word = "word";
        final var responsesGetDictionaryWordMeaning = new ArrayList<ResponseGetDictionaryWordMeaning>() {
            {
                add(new ResponseGetDictionaryWordMeaning("Noun", "Something that can be written or told so"));
            }
        };

        final var englishDictionaryWordResponse = Mockito.mock(EnglishDictionaryWordResponse.class);
        final var englishDictionaryWordResponses = List.of(englishDictionaryWordResponse);
        final var dictionaryWordResponse = Mockito.mock(DictionaryWordResponse.class);
        final Response<List<EnglishDictionaryWordResponse>> response = Response.success(englishDictionaryWordResponses);
        final Call<List<EnglishDictionaryWordResponse>> call = Mockito.mock(Call.class);

        Mockito.when(englishDictionaryAPI.get(word)).thenReturn(call);
        Mockito.when(call.execute()).thenReturn(response);
        Mockito.when(this.dictionaryWordResponseAdapter.getWordResponse(englishDictionaryWordResponses)).thenReturn(dictionaryWordResponse);
        Mockito.when(dictionaryWordResponse.listMeanings()).thenReturn(responsesGetDictionaryWordMeaning);

        final var responseEntity = this.englishDictionaryWordsController.list(word);
        final var cacheControlHeader = responseEntity.getHeaders().get("Cache-Control");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responsesGetDictionaryWordMeaning, responseEntity.getBody().meanings());
        assertNotNull(cacheControlHeader);
        assertEquals("max-age=86400, must-revalidate, no-transform, public", cacheControlHeader.get(0));


        Mockito.verify(englishDictionaryAPI, Mockito.times(1)).get(word);
        Mockito.verify(call, Mockito.times(1)).execute();
        Mockito.verify(this.dictionaryWordResponseAdapter, Mockito.times(1)).getWordResponse(englishDictionaryWordResponses);
        Mockito.verify(dictionaryWordResponse, Mockito.times(1)).listMeanings();
    }

    @Test
    void getNullWordResponse() throws Exception {
        final var word = "word";

        final var englishDictionaryWordResponse = Mockito.mock(EnglishDictionaryWordResponse.class);
        final var englishDictionaryWordResponses = List.of(englishDictionaryWordResponse);
        final Response<List<EnglishDictionaryWordResponse>> response = Response.success(englishDictionaryWordResponses);
        final Call<List<EnglishDictionaryWordResponse>> call = Mockito.mock(Call.class);

        Mockito.when(englishDictionaryAPI.get(word)).thenReturn(call);
        Mockito.when(call.execute()).thenReturn(response);
        Mockito.when(this.dictionaryWordResponseAdapter.getWordResponse(englishDictionaryWordResponses)).thenReturn(null);

        final var responseEntity = this.englishDictionaryWordsController.list(word);
        final var cacheControlHeader = responseEntity.getHeaders().get("Cache-Control");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody().meanings());
        assertNotNull(cacheControlHeader);
        assertEquals("max-age=86400, must-revalidate, no-transform, public", cacheControlHeader.get(0));


        Mockito.verify(englishDictionaryAPI, Mockito.times(1)).get(word);
        Mockito.verify(call, Mockito.times(1)).execute();
        Mockito.verify(this.dictionaryWordResponseAdapter, Mockito.times(1)).getWordResponse(englishDictionaryWordResponses);
    }

    @Test
    void getCallIOException() throws Exception {
        final var word = "word";
        final var iOException = new IOException();

        final Call<List<EnglishDictionaryWordResponse>> call = Mockito.mock(Call.class);

        Mockito.when(englishDictionaryAPI.get(word)).thenReturn(call);
        Mockito.when(call.execute()).thenThrow(iOException);

        final var runtimeException = assertThrows(RuntimeException.class, () -> this.englishDictionaryWordsController.list(word));

        assertEquals(iOException, runtimeException.getCause());


        Mockito.verify(englishDictionaryAPI, Mockito.times(1)).get(word);
        Mockito.verify(call, Mockito.times(1)).execute();
        Mockito.verify(this.dictionaryWordResponseAdapter, Mockito.times(0)).getWordResponse(Mockito.anyList());
    }
}