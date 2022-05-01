package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.files.FileLinesStreamer;
import domain.persistence.entities.enums.Language;
import domain.requests.gets.lists.RequestGetListGameHelp;
import domain.requests.gets.lists.RequestGetListGreenLetter;
import domain.responses.gets.lists.ResponseGetListGameHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = UriConstants.Games.Language.Helps.URL)
public class GameHelpsController {

    private final FileLinesStreamer fileLinesStreamer;

    @Autowired
    public GameHelpsController(FileLinesStreamer fileLinesStreamer) {
        this.fileLinesStreamer = fileLinesStreamer;
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetListGameHelp> list(@PathVariable Language language, RequestGetListGameHelp requestGetListGameHelp)
    {
        //TODO: Validate pagination.

        final var requestsGetListGreenLetters = requestGetListGameHelp.getIndexedGreenLetters();
        final var lines = this.fileLinesStreamer.list(language.getPathWordsFile());
        final var words = lines.filter(s -> this.isValid(s, requestGetListGameHelp, requestsGetListGreenLetters))
                                                .toList();

        lines.close();

        final var responseGetListGameHelp = new ResponseGetListGameHelp(words);

        return ResponseEntity.ok(responseGetListGameHelp);
    }

    private boolean isValid(String word, RequestGetListGameHelp requestGetListGameHelp, List<RequestGetListGreenLetter> requestsGetListGreenLetter) {
        return requestGetListGameHelp.isValid(word) && requestsGetListGreenLetter.stream().allMatch(rglgl -> rglgl.isValid(word));
    }
}
