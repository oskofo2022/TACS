package controllers;




import domain.integrations.APIs.dictionaries.spanish.SpanishDictionaryAPI;
import domain.integrations.APIs.dictionaries.spanish.entities.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SpanishDictionaryControllerTest {

    @Mock
    private SpanishDictionaryAPI spanishDictionaryAPI;

    @InjectMocks
    private SpanishDictionaryController spanishDictionaryController;


    @Test
    void getList() throws IOException {

        String word = "trato";

        List<String> definitions = new ArrayList<>();
        definitions.add("Relación de una persona con otra o con otras");
        definitions.add("Forma o manera como se relaciona una persona con otras o con los animales");
        definitions.add("Manera de usar o manejar cierta cosa");
        definitions.add("Acuerdo al que llegan dos o más personas sobre un asunto");

        SpanishDictionaryWordSense spanishDictionaryWordSense = new SpanishDictionaryWordSense();
        spanishDictionaryWordSense.setDefinitions(definitions);
        List<SpanishDictionaryWordSense> senses = new ArrayList<>();
        senses.add(spanishDictionaryWordSense);

        SpanishDictionaryWordEntry spanishDictionaryWordEntry = new SpanishDictionaryWordEntry();
        spanishDictionaryWordEntry.setSenses(senses);
        List<SpanishDictionaryWordEntry> wordEntries = new ArrayList<>();
        wordEntries.add(spanishDictionaryWordEntry);

        SpanishDictionaryWordLexicalEntry spanishDictionaryWordLexicalEntry = new SpanishDictionaryWordLexicalEntry();
        spanishDictionaryWordLexicalEntry.setEntries(wordEntries);
        List<SpanishDictionaryWordLexicalEntry> wordLexicalEntries = new ArrayList<>();
        wordEntries.add(spanishDictionaryWordEntry);

        SpanishDictionaryWordResponseResult spanishDictionaryWordResponseResult = new SpanishDictionaryWordResponseResult();
        spanishDictionaryWordResponseResult.setLexicalEntries(wordLexicalEntries);
        List<SpanishDictionaryWordResponseResult> wordResponseResults = new ArrayList<>();
        wordResponseResults.add(spanishDictionaryWordResponseResult);

        SpanishDictionaryWordResponse spanishDictionaryWordResponse = new SpanishDictionaryWordResponse();
        spanishDictionaryWordResponse.setResults(wordResponseResults);

        final var mockedCall = Mockito.mock(Call.class);
        final var spaResponse = Response.success(spanishDictionaryWordResponse);

        Mockito.when(mockedCall.execute()).thenReturn(spaResponse);
        Mockito.when(this.spanishDictionaryController.tryGetResponse(mockedCall)).thenReturn(spanishDictionaryWordResponse);

        final var response = this.spanishDictionaryController.tryGetResponse(this.spanishDictionaryAPI.get(word));

        assertEquals(4, response.listMeanings().size());
        assertEquals("Relación de una persona con otra o con otras", response.listMeanings().get(0));

    }

}
