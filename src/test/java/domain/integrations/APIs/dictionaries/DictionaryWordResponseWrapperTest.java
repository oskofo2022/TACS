package domain.integrations.APIs.dictionaries;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DictionaryWordResponseWrapperTest {

    @Mock
    private DictionaryWordResponseSupplier dictionaryWordResponseSupplier;

    private DictionaryWordResponseWrapper dictionaryWordResponseWrapper;

    @Test
    void listMeanings() {
        this.dictionaryWordResponseWrapper = new DictionaryWordResponseWrapper(List.of(this.dictionaryWordResponseSupplier));

        final var responsesGetDictionaryWordMeaning = new ArrayList<ResponseGetDictionaryWordMeaning>() {
            {
                add(new ResponseGetDictionaryWordMeaning("type", "definition"));
            }
        };
        Mockito.when(this.dictionaryWordResponseSupplier.listMeanings()).thenReturn(responsesGetDictionaryWordMeaning.stream());

        assertEquals(responsesGetDictionaryWordMeaning, this.dictionaryWordResponseWrapper.listMeanings());

        Mockito.verify(this.dictionaryWordResponseSupplier, Mockito.times(1)).listMeanings();
    }

    @Test
    void listMeaningsWithoutResponses() {
        this.dictionaryWordResponseWrapper = new DictionaryWordResponseWrapper(null);

        final var responsesGetDictionaryWordMeaning = this.dictionaryWordResponseWrapper.listMeanings();
        assertNotNull(responsesGetDictionaryWordMeaning);
        assertTrue(responsesGetDictionaryWordMeaning.isEmpty());
    }
}