package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.repositories.entities.Language;
import domain.responses.gets.lists.ResponseGetDictionaryWord;
import domain.responses.gets.lists.ResponseGetDictionaryWordMeaning;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = UriConstants.Dictionaries.Words.URL)
public class DictionariesWordsController {

    @GetMapping(path = UriConstants.Dictionaries.Words.Word, produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetDictionaryWord> list(@PathVariable Language language, @PathVariable String word)
    {
        var responsesGetDictionaryWordMeaning = new ArrayList<ResponseGetDictionaryWordMeaning>() {
            {
                add(new ResponseGetDictionaryWordMeaning("noun", "is a word"));
            }
        };
        var responseGetDictionaryWord = new ResponseGetDictionaryWord(responsesGetDictionaryWordMeaning);

        return ResponseEntity.ok(responseGetDictionaryWord);
    }

}
