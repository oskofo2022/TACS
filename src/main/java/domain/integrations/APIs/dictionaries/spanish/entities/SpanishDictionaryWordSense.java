package domain.integrations.APIs.dictionaries.spanish.entities;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.List;
import java.util.stream.Stream;

public class SpanishDictionaryWordSense {
    private List<String> definitions;

    public List<String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }

    public Stream<ResponseGetDictionaryWordMeaning> listMeanings(SpanishDictionaryWordLexicalCategory lexicalCategory) {
        return this.definitions.stream().map(s -> new ResponseGetDictionaryWordMeaning(lexicalCategory.getText(), s));
    }
}
