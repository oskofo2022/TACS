package domain.integrations.APIs.dictionaries.english.entities;

import domain.integrations.APIs.dictionaries.DictionaryWordResponseSupplier;
import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.List;
import java.util.stream.Stream;

public class EnglishDictionaryWordResponse implements DictionaryWordResponseSupplier {
    private List<EnglishDictionaryWordMeaning> meanings;

    public List<EnglishDictionaryWordMeaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<EnglishDictionaryWordMeaning> meanings) {
        this.meanings = meanings;
    }

    public Stream<ResponseGetDictionaryWordMeaning> listMeanings() {
        return this.meanings.stream()
                            .map(edwm -> new ResponseGetDictionaryWordMeaning(edwm.getPartOfSpeech(), edwm.getDefinitions().get(0).getDefinition()));
    }
}
