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
class SpanishDictionaryWordEntryTest {

    private SpanishDictionaryWordLexicalCategory spanishDictionaryWordLexicalCategory;
    @Mock
    private SpanishDictionaryWordSense spanishDictionaryWordSense;
    @InjectMocks
    private SpanishDictionaryWordEntry spanishDictionaryWordEntry;

    @BeforeEach
    private void setUp() {
        this.spanishDictionaryWordLexicalCategory = new SpanishDictionaryWordLexicalCategory();
        this.spanishDictionaryWordEntry.setSenses(List.of(this.spanishDictionaryWordSense));
    }

    @Test
    void listMeanings() {
        final var responsesGetDictionaryWordMeaning = new ArrayList<ResponseGetDictionaryWordMeaning>() {
            {
                add(new ResponseGetDictionaryWordMeaning("type", "definition"));
            }
        };

        Mockito.when(this.spanishDictionaryWordSense.listMeanings(this.spanishDictionaryWordLexicalCategory)).thenReturn(responsesGetDictionaryWordMeaning.stream());


        assertEquals(responsesGetDictionaryWordMeaning, this.spanishDictionaryWordEntry.listMeanings(this.spanishDictionaryWordLexicalCategory).toList());

        Mockito.verify(this.spanishDictionaryWordSense, Mockito.times(1)).listMeanings(this.spanishDictionaryWordLexicalCategory);
    }
}