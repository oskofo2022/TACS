package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.runtime.DuplicateEntityFoundRuntimeException;
import domain.persistence.entities.Match;
import domain.persistence.queries.SpecificationBuilder;
import domain.persistence.repositories.MatchRepository;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListUserMatch;
import domain.requests.posts.RequestPostUserMatchToday;
import domain.responses.gets.lists.ResponseGetListUserMatch;
import domain.responses.gets.lists.ResponseGetPagedList;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
public class UserMatchesController extends PagedListController {

    private final MatchRepository matchRepository;
    private final UserContextService userContextService;

    public UserMatchesController(MatchRepository matchRepository, UserContextService userContextService) {
        this.matchRepository = matchRepository;
        this.userContextService = userContextService;
    }

    @GetMapping(path = UriConstants.Users.Myself.Matches.URL, produces = MediaTypeConstants.JSON)
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListUserMatch>> list(@Valid RequestGetListUserMatch requestGetListUserMatch) {
        final var user = this.userContextService.get();
        requestGetListUserMatch.setUserId(user.getId());

        final var pagedList = this.list(this.matchRepository, requestGetListUserMatch, (m) -> new ResponseGetListUserMatch(m.getDate(), m.getLanguage()));

        return ResponseEntity.ok(pagedList);
    }

    @PostMapping(path = UriConstants.Users.Myself.Matches.Today.URL, consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
    @Transactional
    public ResponseEntity<Void> post(@Valid @RequestBody RequestPostUserMatchToday requestPostUserMatchToday) {
        final var user = this.userContextService.get();

        final var specificationBuilder = new SpecificationBuilder();

        specificationBuilder.andEqual("user.id", user.getId())
                            .andEqual("date", LocalDate.now());

        this.matchRepository.findAll(specificationBuilder.build())
                                                         .stream()
                                                         .filter(requestPostUserMatchToday::hasLanguage)
                                                         .findAny()
                                                         .ifPresent(m -> { throw new DuplicateEntityFoundRuntimeException(Match.class); });

        final var matches = requestPostUserMatchToday.listMatches(user);

        this.matchRepository.saveAll(matches);

        return ResponseEntity.ok()
                             .build();
    }
}
