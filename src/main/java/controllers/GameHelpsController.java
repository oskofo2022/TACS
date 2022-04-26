package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.persistence.entities.enums.Language;
import domain.requests.gets.lists.RequestGetListGameHelp;
import domain.responses.gets.lists.ResponseGetListGameHelp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = UriConstants.Games.Language.Helps.URL)
public class GameHelpsController {
    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetListGameHelp> list(@PathVariable Language language, RequestGetListGameHelp requestGetListGameHelp)
    {
        //TODO: Implement scanner reading file using dictionary and use letters.

        final var responseGetListGameHelp = new ResponseGetListGameHelp(new ArrayList<>() {
            {
                add("board");
                add("sushi");
            }
        });

        return ResponseEntity.ok(responseGetListGameHelp);
    }
}
