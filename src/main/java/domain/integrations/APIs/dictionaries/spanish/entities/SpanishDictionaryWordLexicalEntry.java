package domain.integrations.APIs.dictionaries.spanish.entities;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.List;
import java.util.stream.Stream;

public class SpanishDictionaryWordLexicalEntry {
    private List<SpanishDictionaryWordEntry> entries;
    private SpanishDictionaryWordLexicalCategory lexicalCategory;

    public SpanishDictionaryWordLexicalCategory getLexicalCategory() {
        return lexicalCategory;
    }

    public void setLexicalCategory(SpanishDictionaryWordLexicalCategory lexicalCategory) {
        this.lexicalCategory = lexicalCategory;
    }

    public List<SpanishDictionaryWordEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<SpanishDictionaryWordEntry> entries) {
        this.entries = entries;
    }

    public Stream<ResponseGetDictionaryWordMeaning> listMeanings() {
        return this.entries.stream().flatMap(sdwe -> sdwe.listMeanings(this.lexicalCategory));
    }
}
