package domain.integrations.APIs.dictionaries.english.entities;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.List;
import java.util.Optional;

public class EnglishDictionaryWordMeaning {
    private String partOfSpeech;
    private List<EnglishDictionaryWordMeaningDefinition> definitions;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<EnglishDictionaryWordMeaningDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<EnglishDictionaryWordMeaningDefinition> definitions) {
        this.definitions = definitions;
    }

    public Optional<ResponseGetDictionaryWordMeaning> getMeaning() {
        return this.definitions.stream().findFirst().map(edwmd -> new ResponseGetDictionaryWordMeaning(this.partOfSpeech, edwmd.getDefinition()));
    }
}
