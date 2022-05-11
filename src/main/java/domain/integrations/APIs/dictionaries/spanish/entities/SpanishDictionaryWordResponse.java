package domain.integrations.APIs.dictionaries.spanish.entities;

import domain.integrations.APIs.dictionaries.DictionaryWordResponse;
import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.List;

public class SpanishDictionaryWordResponse implements DictionaryWordResponse {
    private List<SpanishDictionaryWordResponseResult> results;

    public List<SpanishDictionaryWordResponseResult> getResults() {
        return results;
    }

    public void setResults(List<SpanishDictionaryWordResponseResult> results) {
        this.results = results;
    }

    public List<ResponseGetDictionaryWordMeaning> listMeanings() {
        return this.results.stream()
                           .flatMap(SpanishDictionaryWordResponseResult::listMeanings)
                           .toList();
    }
}
