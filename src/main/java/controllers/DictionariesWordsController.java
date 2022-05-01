package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.responses.gets.lists.ResponseGetDictionaryWord;
import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public abstract class DictionariesWordsController {

    @GetMapping(path = UriConstants.Dictionaries.Word, produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetDictionaryWord> list(@PathVariable String word)
    {
        final var responsesGetDictionaryWordMeaning = this.getMeanings(word);
        final var responseGetDictionaryWord = new ResponseGetDictionaryWord(responsesGetDictionaryWordMeaning);
        return ResponseEntity.ok(responseGetDictionaryWord);
    }

    abstract protected List<ResponseGetDictionaryWordMeaning> getMeanings(String word);
}
