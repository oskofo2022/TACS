package domain.integrations.APIs.dictionaries.english;

import domain.integrations.APIs.dictionaries.DictionaryWordResponse;
import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.List;

public record EnglishDictionaryWordResponseWrapper(List<EnglishDictionaryWordResponse> responses) implements DictionaryWordResponse {
    @Override
    public List<ResponseGetDictionaryWordMeaning> listMeanings() {
        return this.responses.stream()
                             .flatMap(EnglishDictionaryWordResponse::listMeanings)
                             .toList();
    }
}
