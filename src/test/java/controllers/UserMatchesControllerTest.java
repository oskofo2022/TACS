package controllers;

import constants.SuppressWarningsConstants;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void postWithoutDuplicatedFound(){
        final var idUser = UUID.randomUUID();

        final var user = new User();
        user.setId(idUser);
        user.setName("someName");
        user.setEmail("some@email.com");
        user.setPassword("somePassword");

        RequestPostUserMatchTodayResult requestPostUserMatchTodayResultOne = new RequestPostUserMatchTodayResult();
        requestPostUserMatchTodayResultOne.setLanguage(Language.SPANISH);
        requestPostUserMatchTodayResultOne.setGuessesCount(3);

        RequestPostUserMatchTodayResult requestPostUserMatchTodayResultTwo = new RequestPostUserMatchTodayResult();
        requestPostUserMatchTodayResultTwo.setLanguage(Language.ENGLISH);
        requestPostUserMatchTodayResultTwo.setGuessesCount(4);

        final var listRequestPostUserMatchTodayResult = new ArrayList<RequestPostUserMatchTodayResult>();
        listRequestPostUserMatchTodayResult.add(requestPostUserMatchTodayResultOne);
        listRequestPostUserMatchTodayResult.add(requestPostUserMatchTodayResultTwo);

        RequestPostUserMatchToday requestPostUserMatchToday = Mockito.mock(RequestPostUserMatchToday.class);

        final var matchList = new ArrayList<Match>();

        Mockito.when(this.userContextService.get()).thenReturn(user);
        Mockito.when(this.matchRepository.findAll(Mockito.any(Specification.class))).thenReturn(matchList);

        final var responseEntity = this.userMatchesController.post(requestPostUserMatchToday);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Mockito.verify(this.matchRepository,Mockito.times(1)).findAll(Mockito.any(Specification.class));
        Mockito.verify(this.matchRepository,Mockito.times(1)).saveAll(matchList);
        Mockito.verify(this.userContextService,Mockito.times(1)).get();
    }
}
