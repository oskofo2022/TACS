package domain.integrations.APIs.dictionaries.english;

import java.util.List;

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
}
