package domain.integrations.APIs.dictionaries;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryWordResponseAdapter {
    public DictionaryWordResponse getWordResponse(List<? extends DictionaryWordResponseSupplier> responses) {
        return new DictionaryWordResponseWrapper(responses);
    }
}
