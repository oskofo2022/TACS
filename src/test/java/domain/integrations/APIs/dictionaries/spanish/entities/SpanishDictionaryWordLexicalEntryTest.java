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
class SpanishDictionaryWordLexicalEntryTest {

    private SpanishDictionaryWordLexicalCategory spanishDictionaryWordLexicalCategory;
    @Mock
    private SpanishDictionaryWordEntry spanishDictionaryWordEntry;
    @InjectMocks
    private SpanishDictionaryWordLexicalEntry spanishDictionaryWordLexicalEntry;

    @BeforeEach
    private void setUp() {
        this.spanishDictionaryWordLexicalCategory = new SpanishDictionaryWordLexicalCategory();
        this.spanishDictionaryWordLexicalEntry.setLexicalCategory(this.spanishDictionaryWordLexicalCategory);
        this.spanishDictionaryWordLexicalEntry.setEntries(List.of(this.spanishDictionaryWordEntry));
    }

    @Test
    void listMeanings() {
        final var responsesGetDictionaryWordMeaning = new ArrayList<ResponseGetDictionaryWordMeaning>() {
            {
                add(new ResponseGetDictionaryWordMeaning("type", "definition"));
            }
        };

        Mockito.when(this.spanishDictionaryWordEntry.listMeanings(this.spanishDictionaryWordLexicalCategory)).thenReturn(responsesGetDictionaryWordMeaning.stream());


        assertEquals(responsesGetDictionaryWordMeaning, this.spanishDictionaryWordLexicalEntry.listMeanings().toList());

        Mockito.verify(this.spanishDictionaryWordEntry, Mockito.times(1)).listMeanings(this.spanishDictionaryWordLexicalCategory);
    }
}