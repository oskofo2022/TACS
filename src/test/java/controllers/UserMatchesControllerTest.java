package controllers;

import constants.SuppressWarningsConstants;
import domain.errors.runtime.DuplicateEntityFoundRuntimeException;
import domain.persistence.entities.Match;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.repositories.MatchRepository;
import domain.persistence.sessions.UserContextService;
import domain.requests.posts.RequestPostUserMatchToday;
import domain.requests.posts.RequestPostUserMatchTodayResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;


import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
public class UserMatchesControllerTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private UserContextService userContextService;

    @InjectMocks
    private UserMatchesController userMatchesController;

    @Test
    void post() {
        final var idUser = UUID.randomUUID();

        final var user = new User();
        user.setId(idUser);

        final var matches = new ArrayList<Match>() {
            {
                add(new Match());
            }
        };

        final var requestPostUserMatchToday = Mockito.mock(RequestPostUserMatchToday.class);

        Mockito.when(this.userContextService.get()).thenReturn(user);
        Mockito.when(requestPostUserMatchToday.listMatches(user)).thenReturn(matches);

        final var responseEntity = this.userMatchesController.post(requestPostUserMatchToday);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Mockito.verify(this.userContextService, Mockito.times(1)).get();
        Mockito.verify(this.matchRepository, Mockito.times(1)).findOne(Mockito.any(Specification.class));
        Mockito.verify(requestPostUserMatchToday, Mockito.times(1)).listMatches(user);
        Mockito.verify(this.matchRepository, Mockito.times(1)).saveAll(matches);
        Mockito.verify(requestPostUserMatchToday, Mockito.never()).hasLanguage(Mockito.any());
    }

    @Test
    void postDuplicateMatch() {
        final var idUser = UUID.randomUUID();

        final var user = new User();
        user.setId(idUser);

        final var requestPostUserMatchToday = Mockito.mock(RequestPostUserMatchToday.class);

        final var existingMatch = new Match();
        Mockito.when(this.userContextService.get()).thenReturn(user);
        Mockito.when(requestPostUserMatchToday.hasLanguage(existingMatch)).thenReturn(true);
        Mockito.when(this.matchRepository.findOne(Mockito.any(Specification.class))).thenReturn(Optional.of(existingMatch));

        assertThrows(DuplicateEntityFoundRuntimeException.class, () -> this.userMatchesController.post(requestPostUserMatchToday));

        Mockito.verify(this.userContextService, Mockito.times(1)).get();
        Mockito.verify(this.matchRepository, Mockito.times(1)).findOne(Mockito.any(Specification.class));
        Mockito.verify(requestPostUserMatchToday, Mockito.times(1)).hasLanguage(existingMatch);
        Mockito.verify(requestPostUserMatchToday, Mockito.never()).listMatches(Mockito.any());
        Mockito.verify(this.matchRepository, Mockito.never()).saveAll(Mockito.any());
    }
}
