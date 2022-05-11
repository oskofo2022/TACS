package domain.integrations.APIs.dictionaries;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.stream.Stream;

public interface DictionaryWordResponseSupplier {
    Stream<ResponseGetDictionaryWordMeaning> listMeanings();
}
