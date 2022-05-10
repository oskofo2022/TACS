package domain.integrations.APIs.dictionaries.spanish;

import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;

import java.util.List;
import java.util.stream.Stream;

public class SpanishDictionaryWordResponseResult {
    private List<SpanishDictionaryWordLexicalEntry> lexicalEntries;

    public List<SpanishDictionaryWordLexicalEntry> getLexicalEntries() {
        return lexicalEntries;
    }

    public void setLexicalEntries(List<SpanishDictionaryWordLexicalEntry> lexicalEntries) {
        this.lexicalEntries = lexicalEntries;
    }

    public Stream<ResponseGetDictionaryWordMeaning> listMeanings() {
        return this.lexicalEntries.stream().flatMap(SpanishDictionaryWordLexicalEntry::listMeanings);
    }
}
