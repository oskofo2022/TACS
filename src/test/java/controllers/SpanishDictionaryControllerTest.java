package controllers;

import constants.SuppressWarningsConstants;
import domain.integrations.APIs.dictionaries.spanish.SpanishDictionaryAPI;

import domain.integrations.APIs.dictionaries.spanish.entities.SpanishDictionaryWordResponse;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
public class SpanishDictionaryControllerTest {

    @Mock
    private SpanishDictionaryAPI spanishDictionaryAPI;

    @InjectMocks
    private SpanishDictionaryWordsController spanishDictionaryController;

    @Test
    void getWordResponse() throws Exception {
        final var word = "palabra";
        final var responsesGetDictionaryWordMeaning = new ArrayList<ResponseGetDictionaryWordMeaning>() {
            {
                add(new ResponseGetDictionaryWordMeaning("Sustantivo", "Algo que puede ser escrito o dicho"));
            }
        };

        final var spanishDictionaryWordResponse = Mockito.mock(SpanishDictionaryWordResponse.class);
        final Response<SpanishDictionaryWordResponse> response = Response.success(spanishDictionaryWordResponse);
        final Call<SpanishDictionaryWordResponse> call = Mockito.mock(Call.class);

        Mockito.when(spanishDictionaryAPI.get(word)).thenReturn(call);
        Mockito.when(call.execute()).thenReturn(response);
        Mockito.when(spanishDictionaryWordResponse.listMeanings()).thenReturn(responsesGetDictionaryWordMeaning);

        final var responseEntity = this.spanishDictionaryController.list(word);
        final var cacheControlHeader = responseEntity.getHeaders().get("Cache-Control");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responsesGetDictionaryWordMeaning, responseEntity.getBody().meanings());
        assertNotNull(cacheControlHeader);
        assertEquals("max-age=86400, must-revalidate, no-transform, public", cacheControlHeader.get(0));

        Mockito.verify(spanishDictionaryAPI, Mockito.times(1)).get(word);
        Mockito.verify(call, Mockito.times(1)).execute();
        Mockito.verify(spanishDictionaryWordResponse, Mockito.times(1)).listMeanings();
    }

    @Test
    void getNullWordResponse() throws Exception {
        final var word = "palabra";
        final Response<SpanishDictionaryWordResponse> response = Response.success(null);
        final Call<SpanishDictionaryWordResponse> call = Mockito.mock(Call.class);

        Mockito.when(spanishDictionaryAPI.get(word)).thenReturn(call);
        Mockito.when(call.execute()).thenReturn(response);

        final var responseEntity = this.spanishDictionaryController.list(word);
        final var cacheControlHeader = responseEntity.getHeaders().get("Cache-Control");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody().meanings());
        assertNotNull(cacheControlHeader);
        assertEquals("max-age=86400, must-revalidate, no-transform, public", cacheControlHeader.get(0));

        Mockito.verify(spanishDictionaryAPI, Mockito.times(1)).get(word);
        Mockito.verify(call, Mockito.times(1)).execute();
    }

    @Test
    void getCallIOException() throws Exception {
        final var word = "palabra";
        final var iOException = new IOException();
        final Response<SpanishDictionaryWordResponse> response = Response.success(null);
        final Call<SpanishDictionaryWordResponse> call = Mockito.mock(Call.class);

        Mockito.when(spanishDictionaryAPI.get(word)).thenReturn(call);
        Mockito.when(call.execute()).thenThrow(iOException);

        final var runtimeException = assertThrows(RuntimeException.class, () -> this.spanishDictionaryController.list(word));

        assertEquals(iOException, runtimeException.getCause());

        Mockito.verify(spanishDictionaryAPI, Mockito.times(1)).get(word);
        Mockito.verify(call, Mockito.times(1)).execute();
    }
}
