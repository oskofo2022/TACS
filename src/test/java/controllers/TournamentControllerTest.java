package controllers;

import constants.SuppressWarningsConstants;
import constants.UriConstants;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListPublicTournament;
import domain.requests.posts.RequestPostTournament;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
public class TournamentControllerTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private UserContextService userContextService;

    @InjectMocks
    private TournamentsController tournamentsController;

    @Test
    void listPublic() {

        final var tournamentOne = new Tournament();
        tournamentOne.setName("Tournament One");
        tournamentOne.setVisibility(Visibility.PUBLIC);
        tournamentOne.setStartDate(LocalDate.now().plusDays(1));
        tournamentOne.setEndDate(LocalDate.now().plusMonths(1));
        tournamentOne.setLanguage(Language.SPANISH);

        final var tournamentTwo = new Tournament();
        tournamentTwo.setName("Tournament Two");
        tournamentTwo.setVisibility(Visibility.PUBLIC);
        tournamentTwo.setStartDate(LocalDate.now().plusDays(1));
        tournamentTwo.setEndDate(LocalDate.now().plusMonths(3));
        tournamentTwo.setLanguage(Language.ENGLISH);

        final var tournamentThree = new Tournament();
        tournamentThree.setName("Tournament Three");
        tournamentThree.setVisibility(Visibility.PRIVATE);
        tournamentThree.setStartDate(LocalDate.now().plusDays(1));
        tournamentThree.setEndDate(LocalDate.now().plusMonths(2));
        tournamentThree.setLanguage(Language.SPANISH);

        final var tournaments = new ArrayList<Tournament>();
        tournaments.add(tournamentOne);
        tournaments.add(tournamentTwo);
        tournaments.add(tournamentThree);

        final long totalElements = 2;
        final int totalPages = 1;

        final var requestGetListPublicTournament = new RequestGetListPublicTournament();

        final var tournamentPage = Mockito.mock(Page.class);
        Mockito.when(tournamentPage.getTotalElements()).thenReturn(totalElements);
        Mockito.when(tournamentPage.getTotalPages()).thenReturn(totalPages);
        Mockito.when(tournamentPage.stream()).thenReturn(tournaments.stream());
        Mockito.when(this.tournamentRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class))).thenReturn(tournamentPage);

        final var responseEntity = this.tournamentsController.listPublic(requestGetListPublicTournament);
        final var responseGetPagedList = responseEntity.getBody();
        final var responseGetListTournament = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(totalElements, responseGetPagedList.totalCount());
        assertEquals(totalPages, responseGetPagedList.pageCount());

        for(int i = 0; i < tournaments.size(); i++){
            assertEquals(tournaments.get(i).getId(), responseGetListTournament.get(i).id());
            assertEquals(tournaments.get(i).getName(), responseGetListTournament.get(i).name());
            assertEquals(tournaments.get(i).getState(), responseGetListTournament.get(i).state());
            assertEquals(tournaments.get(i).getVisibility(), responseGetListTournament.get(i).visibility());
            assertEquals(tournaments.get(i).getStartDate(), responseGetListTournament.get(i).startDate());
            assertEquals(tournaments.get(i).getEndDate(), responseGetListTournament.get(i).endDate());
            assertEquals(tournaments.get(i).getLanguage(), responseGetListTournament.get(i).language());
        }

        Mockito.verify(tournamentPage, Mockito.times(1)).getTotalElements();
        Mockito.verify(tournamentPage, Mockito.times(1)).getTotalPages();
        Mockito.verify(tournamentPage, Mockito.times(1)).stream();
        Mockito.verify(this.tournamentRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class));
    }

    @Test
    void post() {
        final var tournamentId = UUID.randomUUID();
        final var userCreatorId = UUID.randomUUID();

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI(UriConstants.DELIMITER + UriConstants.Tournaments.URL);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        final var requestPostTournament = new RequestPostTournament();
        requestPostTournament.setName("National Tournament");
        requestPostTournament.setLanguage(Language.ENGLISH);
        requestPostTournament.setStartDate(LocalDate.now());
        requestPostTournament.setEndDate(LocalDate.now().plusMonths(1));
        requestPostTournament.setVisibility(Visibility.PUBLIC);

        final var user = new User();
        user.setId(userCreatorId);
        user.setName("someName");
        user.setEmail("some@email.com");
        user.setPassword("someKindOfHardcorePassword");

        final Supplier<Tournament> getArgumentMatcherTournament = () -> Mockito.argThat((Tournament t) -> t.getName().equals(requestPostTournament.getName())
                                                                        && t.getLanguage().equals(requestPostTournament.getLanguage()) && t.getVisibility().equals(requestPostTournament.getVisibility()) && t.getStartDate().equals(requestPostTournament.getStartDate())
                                                                        && t.getEndDate().equals(requestPostTournament.getEndDate()) && t.getVisibility().equals(requestPostTournament.getVisibility()));

        Mockito.when(this.userContextService.get()).thenReturn(user);
        Mockito.when(this.tournamentRepository.save(getArgumentMatcherTournament.get())).then((iom) -> {
            final var tournament = iom.getArgument(0, Tournament.class);
            tournament.setId(tournamentId);
            return tournament;
        });

        final var responseEntity = this.tournamentsController.post(requestPostTournament);
        final var headerLocation = responseEntity.getHeaders().get("Location");

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(tournamentId, responseEntity.getBody().getId());
        assertNotNull(headerLocation);
        assertEquals("http://localhost/users/myself/tournaments?id=%s".formatted(tournamentId), headerLocation.get(0));

        Mockito.verify(this.userContextService, Mockito.times(1)).get();
        Mockito.verify(this.tournamentRepository, Mockito.times(1)).save(getArgumentMatcherTournament.get());
    }
}
