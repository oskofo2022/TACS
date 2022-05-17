package controllers;

import constants.SuppressWarningsConstants;
import domain.files.FileLinesStreamer;
import domain.persistence.entities.enums.Language;
import domain.requests.gets.lists.RequestGetListGameHelp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
public class GameHelpsControllerTest {

    @Mock
    private FileLinesStreamer fileLinesStreamer;
    @InjectMocks
    private GameHelpsController gameHelpsController;

    @Test
    void list(){

        final var totalWordsResponse = 1;

        final var requestGetListGameHelp = new RequestGetListGameHelp();
        requestGetListGameHelp.setBadLetters("oievj");
        requestGetListGameHelp.setGoodLetters("sca");
        requestGetListGameHelp.setGreenLetters("asc--");

        var lines = Mockito.mock(Stream.class, Mockito.RETURNS_DEEP_STUBS);
        var possibleWords = new ArrayList<String>();
        possibleWords.add("ascua");

        Mockito.when(this.fileLinesStreamer.list(Mockito.anyString())).thenReturn(lines);
        Mockito.when(lines.filter(Mockito.any(Predicate.class)).toList()).thenReturn(possibleWords);

        final var responseEntity = gameHelpsController.list(Language.SPANISH, requestGetListGameHelp);
        final var responseGetListGameHelp = responseEntity.getBody();
        final var words = responseGetListGameHelp.words();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(words);
        assertEquals(totalWordsResponse, words.size());
        assertEquals("ascua", words.get(0));
        assertEquals("max-age=86400, must-revalidate, no-transform, public", responseEntity.getHeaders().getCacheControl());

        Mockito.verify(this.fileLinesStreamer, Mockito.times(1)).list(Mockito.anyString());
        Mockito.verify(lines, Mockito.times(1)).filter(Mockito.any(Predicate.class));
    }
}
