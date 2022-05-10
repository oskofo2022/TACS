package domain.integrations.APIs.dictionaries.spanish;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.List;
import java.util.stream.Stream;

public class SpanishDictionaryWordEntry {
    private List<SpanishDictionaryWordSense> senses;

    public List<SpanishDictionaryWordSense> getSenses() {
        return senses;
    }

    public void setSenses(List<SpanishDictionaryWordSense> senses) {
        this.senses = senses;
    }

    public Stream<ResponseGetDictionaryWordMeaning> listMeanings(SpanishDictionaryWordLexicalCategory lexicalCategory) {
        return this.senses.stream().flatMap(sdws -> sdws.listMeanings(lexicalCategory));
    }
}
