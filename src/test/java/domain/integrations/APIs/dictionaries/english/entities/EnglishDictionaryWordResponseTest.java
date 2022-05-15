package domain.integrations.APIs.dictionaries.english.entities;

import domain.integrations.APIs.dictionaries.spanish.entities.SpanishDictionaryWordLexicalCategory;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EnglishDictionaryWordResponseTest {

    @Mock
    private EnglishDictionaryWordMeaning englishDictionaryWordMeaning;
    @InjectMocks
    private EnglishDictionaryWordResponse englishDictionaryWordResponse;

    @BeforeEach
    private void setUp() {
        this.englishDictionaryWordResponse.setMeanings(List.of(this.englishDictionaryWordMeaning));
    }

    @Test
    void listMeanings() {
        final var responseGetDictionaryWordMeaning = new ResponseGetDictionaryWordMeaning("type", "definition");
        final var responsesGetDictionaryWordMeaning = new ArrayList<ResponseGetDictionaryWordMeaning>() {
            {
                add(responseGetDictionaryWordMeaning);
            }
        };


        Mockito.when(this.englishDictionaryWordMeaning.getMeaning()).thenReturn(Optional.of(responseGetDictionaryWordMeaning));

        assertEquals(responsesGetDictionaryWordMeaning, this.englishDictionaryWordResponse.listMeanings().toList());

        Mockito.verify(this.englishDictionaryWordMeaning, Mockito.times(1)).getMeaning();
    }
}