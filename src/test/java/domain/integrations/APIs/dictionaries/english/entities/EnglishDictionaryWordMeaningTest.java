package domain.integrations.APIs.dictionaries.english.entities;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;
import org.junit.jupiter.api.Assertions;
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
class EnglishDictionaryWordMeaningTest {
    private String partOfSpeech;
    @Mock
    private EnglishDictionaryWordMeaningDefinition englishDictionaryWordMeaningDefinition;
    @InjectMocks
    private EnglishDictionaryWordMeaning englishDictionaryWordMeaning;

    @BeforeEach
    public void setUp() {
        this.partOfSpeech = "type";
        this.englishDictionaryWordMeaning.setDefinitions(List.of(this.englishDictionaryWordMeaningDefinition));
        this.englishDictionaryWordMeaning.setPartOfSpeech(this.partOfSpeech);
    }

    @Test
    public void getMeaning() {
        final var definition = "definition";

        Mockito.when(this.englishDictionaryWordMeaningDefinition.getDefinition()).thenReturn(definition);

        final var optionalResponseGetDictionaryWordMeaning = this.englishDictionaryWordMeaning.getMeaning();

        optionalResponseGetDictionaryWordMeaning.ifPresentOrElse((rgdwm) -> {
            assertEquals(this.partOfSpeech, rgdwm.type());
            assertEquals(definition, rgdwm.definition());
        }, Assertions::fail);

        Mockito.verify(this.englishDictionaryWordMeaningDefinition, Mockito.times(1)).getDefinition();
    }
}