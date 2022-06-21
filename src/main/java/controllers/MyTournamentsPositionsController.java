package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.constants.ErrorMessageConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.Tournament;
import domain.persistence.sessions.UserContextService;
import domain.requests.gets.lists.RequestGetListPosition;
import domain.requests.gets.lists.RequestGetListTournamentPosition;
import domain.responses.gets.lists.ResponseGetListTournamentPosition;
import domain.responses.gets.lists.ResponseGetListTournamentPositionResult;
import domain.responses.gets.lists.ResponseGetPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class MyTournamentsPositionsController {

    private final UserContextService userContextService;

    @Autowired
    public MyTournamentsPositionsController(UserContextService userContextService) {
        this.userContextService = userContextService;
    }

    @GetMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.Positions.URL, produces = MediaTypeConstants.JSON)
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListTournamentPosition>> list(@Valid RequestGetListTournamentPosition requestGetListTournamentPosition) {
        final var user = this.userContextService.get();

        final var responseGetPagedList = requestGetListTournamentPosition.paginate(() -> user.getInscribedTournaments()
                                                                                                                                         .stream(),
                                                                                                                               t -> new ResponseGetListTournamentPosition(t.getId(),
                                                                                                                                                                          t.getName(),
                                                                                                                                                                          t.getState(),
                                                                                                                                                                          t.getStartDate(),
                                                                                                                                                                          t.getEndDate(),
                                                                                                                                                                          t.getVisibility(),
                                                                                                                                                                          t.getLanguage(),
                                                                                                                                                                          this.listSummarizedPositions(t)));
        return ResponseEntity.ok(responseGetPagedList);
    }

    @GetMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.One.Positions.URL,produces = MediaTypeConstants.JSON)
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListTournamentPositionResult>> list(@PathVariable UUID tournamentId, @Valid RequestGetListPosition requestGetListPosition) {
        final var user = this.userContextService.get();

        final var tournament = user.getInscribedTournaments()
                                              .stream()
                                              .filter(t -> t.getId().equals(tournamentId))
                                              .findFirst()
                                              .orElseThrow(() -> new EntityNotFoundRuntimeException(ErrorMessageConstants.Entities.Names.TOURNAMENT, Tournament.class));

        final Supplier<Stream<ResponseGetListTournamentPositionResult>> positionSupplier = tournament::listPositions;

        requestGetListPosition.setStartCardinal(tournament);
        final var responseGetPagedList = requestGetListPosition.paginate(positionSupplier, requestGetListPosition::setCardinal);
        return ResponseEntity.ok(responseGetPagedList);
    }

    private List<ResponseGetListTournamentPositionResult> listSummarizedPositions(Tournament tournament) {
        final var responsesGetListTournamentPositionResult = tournament.listPositions()
                                                                                                          .sorted(Comparator.comparing(ResponseGetListTournamentPositionResult::getGuessesCount))
                                                                                                          .limit(15)
                                                                                                          .toList();

        IntStream.range(0, responsesGetListTournamentPositionResult.size())
                 .forEach(i -> responsesGetListTournamentPositionResult.get(i).setCardinal(i + 1));

        return responsesGetListTournamentPositionResult;
    }
}
