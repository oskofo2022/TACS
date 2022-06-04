package controllers;

import constants.SuppressWarningsConstants;
import domain.files.FileLinesStreamer;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.Visibility;
import domain.requests.gets.lists.RequestGetListGameHelp;
import domain.requests.gets.lists.RequestGetListUserInscription;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
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
    void list() {
        final var firstWord = "first";
        final var secondWord = "second";

        final var words = Arrays.asList(firstWord, secondWord);
        final var requestGetListGameHelp = new RequestGetListGameHelp();

        Mockito.when(this.fileLinesStreamer.list(Mockito.anyString())).thenReturn(words.stream(), words.stream());

        final var responseEntity = this.gameHelpsController.list(Language.ENGLISH, requestGetListGameHelp);
        final var responseGetPagedList = responseEntity.getBody();
        final var responseGetListGameHelp = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseGetListGameHelp.size(), responseGetPagedList.totalCount());
        assertEquals(1, responseGetPagedList.pageCount());
        assertEquals("max-age=86400, must-revalidate, no-transform, public", responseEntity.getHeaders().getCacheControl());

        for(int i = 0; i < responseGetListGameHelp.size(); i++){
            assertEquals(words.get(i), responseGetListGameHelp.get(i).word());
        }

        Mockito.verify(this.fileLinesStreamer, Mockito.times(2)).list(Mockito.anyString());
    }
}
