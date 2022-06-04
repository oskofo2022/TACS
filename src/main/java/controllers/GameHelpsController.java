package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.files.FileLinesStreamer;
import domain.persistence.entities.enums.Language;
import domain.requests.gets.lists.RequestGetListGameHelp;
import domain.responses.gets.lists.ResponseGetListGameHelp;
import domain.responses.gets.lists.ResponseGetPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = UriConstants.Games.Language.Helps.URL)
public class GameHelpsController {

    private final FileLinesStreamer fileLinesStreamer;

    @Autowired
    public GameHelpsController(FileLinesStreamer fileLinesStreamer) {
        this.fileLinesStreamer = fileLinesStreamer;
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListGameHelp>> list(@PathVariable Language language, RequestGetListGameHelp requestGetListGameHelp)
    {
        final Supplier<Stream<String>> linesSupplier = () -> this.fileLinesStreamer.list(language.getPathWordsFile());

        final var responseGetPagedList = requestGetListGameHelp.paginate(linesSupplier, ResponseGetListGameHelp::new);

        CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.DAYS)
                                                .cachePublic()
                                                .noTransform()
                                                .mustRevalidate();

        return ResponseEntity.ok()
                             .cacheControl(cacheControl)
                             .body(responseGetPagedList);
    }
}
