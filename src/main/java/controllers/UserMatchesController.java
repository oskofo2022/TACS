package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.runtime.DuplicateEntityFoundRuntimeException;
import domain.persistence.entities.Match;
import domain.persistence.queries.SpecificationBuilder;
import domain.persistence.repositories.MatchRepository;
import domain.persistence.sessions.UserContextService;
import domain.requests.posts.RequestPostUserMatchToday;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping(path = UriConstants.Users.Myself.Matches.Today.URL)
public class UserMatchesController {

    private final MatchRepository matchRepository;
    private final UserContextService userContextService;

    public UserMatchesController(MatchRepository matchRepository, UserContextService userContextService) {
        this.matchRepository = matchRepository;
        this.userContextService = userContextService;
    }

    @PostMapping(consumes = MediaTypeConstants.JSON, produces = MediaTypeConstants.JSON)
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
