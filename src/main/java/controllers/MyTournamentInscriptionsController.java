package controllers;

import constants.MediaTypeConstants;
import constants.UriConstants;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.Inscription;
import domain.persistence.entities.InscriptionIdentifier;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.persistence.repositories.GamesRepository;
import domain.persistence.repositories.TournamentRepository;
import domain.persistence.repositories.UserRepository;
import domain.requests.gets.lists.RequestGetListUserInscription;
import domain.responses.common.gets.ResponseCommonGetGuessCharMatching;
import domain.responses.gets.lists.*;
import domain.security.WordleAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;


@RestController
@RequestMapping(path = UriConstants.Users.Myself.Inscriptions.Tournaments.URL)
public class MyTournamentInscriptionsController extends PagedListController{

    private final UserRepository userRepository;
    private final TournamentRepository tournamentRepository;
    private final WordleAuthenticationManager wordleAuthenticationManager;

    private final GamesRepository gamesRepository;

    @Autowired
    public MyTournamentInscriptionsController(UserRepository userRepository, TournamentRepository tournamentRepository, WordleAuthenticationManager wordleAuthenticationManager, GamesRepository gamesRepository) {
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
        this.wordleAuthenticationManager = wordleAuthenticationManager;
        this.gamesRepository = gamesRepository;
    }

    @GetMapping(produces = MediaTypeConstants.JSON)
    public ResponseEntity<ResponseGetPagedList<ResponseGetListUserInscription>> list(RequestGetListUserInscription requestGetListUserInscription)
    {
        // To debugging purposes use hardcoded id

         /*
        final var wordleUser = this.wordleAuthenticationManager.getCurrentUser();

        var user = new User();
        user = this.userRepository.findById(wordleUser.getId()).get();
         */


        /*
        var localDate = LocalDate.now();
        var responseGetListTournament = new ResponseGetListTournament(1, "tournament", Language.ENGLISH, Visibility.PUBLIC, TournamentState.STARTED, localDate, localDate);
        var responsesGetListGameGuess = new ArrayList<ResponseGetListGameGuess>() {
            {
                add(new ResponseGetListGameGuess("lord", new ArrayList<>() {
                    {
                        add(new ResponseCommonGetGuessCharMatching('l', CharProximity.NONE));
                        add(new ResponseCommonGetGuessCharMatching('o', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('r', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('d', CharProximity.HIT));
                    }
                }));
                add(new ResponseGetListGameGuess("bord", new ArrayList<>() {
                    {
                        add(new ResponseCommonGetGuessCharMatching('b', CharProximity.NONE));
                        add(new ResponseCommonGetGuessCharMatching('o', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('r', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('d', CharProximity.HIT));
                    }
                }));
                add(new ResponseGetListGameGuess("word", new ArrayList<>() {
                    {
                        add(new ResponseCommonGetGuessCharMatching('w', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('o', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('r', CharProximity.HIT));
                        add(new ResponseCommonGetGuessCharMatching('d', CharProximity.HIT));
                    }
                }));
            }
        };
        var responsesGetListUserInscriptionGame = new ArrayList<ResponseGetListUserInscriptionGame>() {
            {
                add(new ResponseGetListUserInscriptionGame(5, "word", true, responsesGetListGameGuess));
            }
        };
        var responseGetListUserInscription = new ResponseGetListUserInscription(responseGetListTournament, responsesGetListUserInscriptionGame);

        var responsesGetListUserInscription = new ArrayList<ResponseGetListUserInscription>() {
            {
                add(responseGetListUserInscription);
            }
        };
        ResponseGetPagedList<ResponseGetListUserInscription> responseGetPagedList = new ResponseGetPagedList<>(1, responsesGetListUserInscription, 1);

         */


        var responseGetPagedList = this.list(this.tournamentRepository, requestGetListUserInscription,
                t -> new ResponseGetListUserInscription(new ResponseGetListTournament(t.getId(), t.getName(), t.getLanguage(), t.getVisibility(), t.getState(), t.getStartDate(), t.getEndDate())
                        , new ArrayList<>()));

        return ResponseEntity.ok(responseGetPagedList);
    }

    @PostMapping(produces = MediaTypeConstants.JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> post(long userId, @RequestParam(name = "tournamentId") long tournamentId) {

        final var user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundRuntimeException(User.class));

        var tournament = this.tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundRuntimeException(Tournament.class));

        var inscriptionIdentifier = new InscriptionIdentifier();
        inscriptionIdentifier.setTournamentId(tournamentId);
        inscriptionIdentifier.setUserId(userId);

        var inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setUser(user);
        inscription.setIdentifier(inscriptionIdentifier);

        tournament.addInscription(inscription);

        this.tournamentRepository.save(tournament);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(UriConstants.Tournaments.ID)
                .buildAndExpand(tournamentId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
