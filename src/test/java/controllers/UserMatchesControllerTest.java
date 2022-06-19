package controllers;

import constants.SuppressWarningsConstants;
import domain.errors.runtime.DuplicateEntityFoundRuntimeException;
import domain.persistence.entities.Match;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.repositories.MatchRepository;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListUserMatch;
import domain.requests.posts.RequestPostUserMatchToday;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;


import java.time.LocalDate;
import java.util.ArrayList;
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
    void list() {
        final var userCreator = new User();
        userCreator.setId(UUID.randomUUID());

        final var firstMatch = new Match();
        firstMatch.setLanguage(Language.SPANISH);
        firstMatch.setDate(LocalDate.now());

        final var secondMatch = new Match();
        firstMatch.setLanguage(Language.ENGLISH);
        firstMatch.setDate(LocalDate.now().plusDays(1));

        final var matches = new ArrayList<Match>() {
            {
                add(firstMatch);
                add(secondMatch);
            }
        };

        final long totalElements = 2;
        final var totalPages = 1;


        final var requestGetListUserMatch = new RequestGetListUserMatch();

        final var user = new User();
        user.setId(UUID.randomUUID());

        final var matchPage = Mockito.mock(Page.class);
        Mockito.when(matchPage.getTotalElements()).thenReturn(totalElements);
        Mockito.when(matchPage.getTotalPages()).thenReturn(totalPages);
        Mockito.when(matchPage.stream()).thenReturn(matches.stream());
        Mockito.when(this.userContextService.get()).thenReturn(user);
        Mockito.when(this.matchRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class))).thenReturn(matchPage);

        final var responseEntity =  this.userMatchesController.list(requestGetListUserMatch);
        final var responseGetPagedList = responseEntity.getBody();
        final var responsesGetListUserMatch = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(totalElements, responseGetPagedList.totalCount());
        assertEquals(totalPages, responseGetPagedList.pageCount());

        for (var index = 0; index < matches.size(); index++) {
            assertEquals(matches.get(index).getLanguage(), responsesGetListUserMatch.get(index).language());
            assertEquals(matches.get(index).getDate(), responsesGetListUserMatch.get(index).date());
        }

        Mockito.verify(matchPage, Mockito.times(1)).getTotalElements();
        Mockito.verify(matchPage, Mockito.times(1)).getTotalPages();
        Mockito.verify(matchPage, Mockito.times(1)).stream();
        Mockito.verify(this.userContextService, Mockito.times(1)).get();
        Mockito.verify(this.matchRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class));
    }

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
        Mockito.verify(this.matchRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class));
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
        Mockito.when(this.matchRepository.findAll(Mockito.any(Specification.class))).thenReturn(new ArrayList() {
            {
                add(existingMatch);
            }
        });

        assertThrows(DuplicateEntityFoundRuntimeException.class, () -> this.userMatchesController.post(requestPostUserMatchToday));

        Mockito.verify(this.userContextService, Mockito.times(1)).get();
        Mockito.verify(this.matchRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class));
        Mockito.verify(requestPostUserMatchToday, Mockito.times(1)).hasLanguage(existingMatch);
        Mockito.verify(requestPostUserMatchToday, Mockito.never()).listMatches(Mockito.any());
        Mockito.verify(this.matchRepository, Mockito.never()).saveAll(Mockito.any());
    }
}
