package domain.integrations.APIs.dictionaries.english;

import java.util.List;

public class EnglishDictionaryWordResponse {
    private List<EnglishDictionaryWordMeaning> meanings;

    public List<EnglishDictionaryWordMeaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<EnglishDictionaryWordMeaning> meanings) {
        this.meanings = meanings;
    }
}
