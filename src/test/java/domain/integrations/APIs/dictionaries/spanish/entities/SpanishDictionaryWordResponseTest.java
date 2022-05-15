package domain.integrations.APIs.dictionaries.spanish.entities;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SpanishDictionaryWordResponseTest {

    @Mock
    private SpanishDictionaryWordResponseResult spanishDictionaryWordResponseResult;

    @InjectMocks
    private SpanishDictionaryWordResponse spanishDictionaryWordResponse;

    @BeforeEach
    private void setUp() {
        this.spanishDictionaryWordResponse.setResults(List.of(this.spanishDictionaryWordResponseResult));
    }

    @Test
    void listMeanings() {
        final var responsesGetDictionaryWordMeaning = new ArrayList<ResponseGetDictionaryWordMeaning>() {
            {
                add(new ResponseGetDictionaryWordMeaning("type", "definition"));
            }
        };

        Mockito.when(this.spanishDictionaryWordResponseResult.listMeanings()).thenReturn(responsesGetDictionaryWordMeaning.stream());


        assertEquals(responsesGetDictionaryWordMeaning, this.spanishDictionaryWordResponse.listMeanings());

        Mockito.verify(this.spanishDictionaryWordResponseResult, Mockito.times(1)).listMeanings();
    }
}