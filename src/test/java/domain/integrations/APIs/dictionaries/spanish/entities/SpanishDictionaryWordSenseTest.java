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
class SpanishDictionaryWordSenseTest {
    private String definition;
    @Mock
    private SpanishDictionaryWordLexicalCategory spanishDictionaryWordLexicalCategory;
    @InjectMocks
    private SpanishDictionaryWordSense spanishDictionaryWordSense;

    @BeforeEach
    private void setUp() {
        this.definition = "definition";
        this.spanishDictionaryWordSense.setDefinitions(List.of(this.definition));
    }

    @Test
    void listMeanings() {
        final var type = "type";
        final var responsesGetDictionaryWordMeaning = new ArrayList<ResponseGetDictionaryWordMeaning>() {
            {
                add(new ResponseGetDictionaryWordMeaning(type, definition));
            }
        };

        Mockito.when(this.spanishDictionaryWordLexicalCategory.getText()).thenReturn(type);

        assertEquals(responsesGetDictionaryWordMeaning, this.spanishDictionaryWordSense.listMeanings(this.spanishDictionaryWordLexicalCategory).toList());

        Mockito.verify(this.spanishDictionaryWordLexicalCategory, Mockito.times(1)).getText();
    }
}