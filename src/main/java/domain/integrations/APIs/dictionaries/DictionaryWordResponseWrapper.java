package domain.integrations.APIs.dictionaries;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record DictionaryWordResponseWrapper(List<? extends DictionaryWordResponseSupplier> responses) implements DictionaryWordResponse {
    @Override
    public List<ResponseGetDictionaryWordMeaning> listMeanings() {
        return Optional.ofNullable(this.responses).map(r -> r.stream().flatMap(DictionaryWordResponseSupplier::listMeanings).toList())
                                                  .orElse(new ArrayList<>());
    }
}
