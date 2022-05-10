package domain.integrations.APIs.dictionaries;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.List;

public interface DictionaryWordResponse {
    List<ResponseGetDictionaryWordMeaning> listMeanings();
}
