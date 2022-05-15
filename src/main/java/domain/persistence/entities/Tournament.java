package domain.persistence.entities;

import domain.errors.runtime.TournamentUnauthorizedUserActionRuntimeException;
import domain.persistence.constants.ColumnConstants;
import domain.persistence.constants.TableConstants;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.responses.gets.lists.ResponseGetListTournamentPositionResult;
import domain.security.users.WordleUser;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = TableConstants.Names.TOURNAMENTS)
public class Tournament {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    @Size(max = 60)
    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TournamentState state;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @OneToMany
    @JoinColumn(name = ColumnConstants.Names.TOURNAMENT_ID)
    private List<Inscription> inscriptions;

    @ManyToOne(optional = false)
    @JoinColumn(name = ColumnConstants.Names.USER_CREATOR_ID)
    private User userCreator;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public TournamentState getState() {
        return state;
    }

    public void setState(TournamentState state) {
        this.state = state;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    public void addInscription(Inscription inscription){
        inscriptions.add(inscription);
    }

    public Inscription inscribe(User user) {
        this.getState()
            .validateInscriptionCreation();

        final var inscriptionIdentifier = new InscriptionIdentifier();
        inscriptionIdentifier.setTournamentId(this.getId());
        inscriptionIdentifier.setUserId(user.getId());

        final var inscription = new Inscription();
        inscription.setTournament(this);
        inscription.setUser(user);
        inscription.setIdentifier(inscriptionIdentifier);

        return inscription;
    }

    public void validateAuthority(WordleUser wordleUser) {
        if (wordleUser.getId() != this.userCreator.getId()) {
            throw new TournamentUnauthorizedUserActionRuntimeException();
        }
    }

    public List<ResponseGetListTournamentPositionResult> listPositions() {
        if (!this.hasStarted()) {
            return Collections.emptyList();
        }

        return this.inscriptions.stream()
                                .map(Inscription::getUser)
                                .map(this::getPosition)
                                .sorted(Comparator.comparing(ResponseGetListTournamentPositionResult::guessesCount))
                                .toList();

    }

    private ResponseGetListTournamentPositionResult getPosition(User user) {
        final var actualDate = LocalDate.now();
        final var topDate = actualDate.isAfter(this.endDate) ? this.endDate : actualDate;
        var deltaDays = ChronoUnit.DAYS.between(this.startDate, topDate);

        final var localDateGuessesCountMap = user.getMatches()
                                                                     .stream()
                                                                     .filter(m -> m.getLanguage() == this.language && this.isInDateRange(m, topDate))
                                                                     .collect(Collectors.toMap(Match::getDate, Match::getGuessesCount));

        var score = this.getScore(localDateGuessesCountMap, this.startDate);
        while (deltaDays > 0) {
            score += this.getScore(localDateGuessesCountMap, this.startDate.plusDays(deltaDays));
            deltaDays--;
        }

        return new ResponseGetListTournamentPositionResult(user.getName(), score);
    }

    private boolean isInDateRange(Match match, LocalDate topDate) {
        return match.getDate().compareTo(this.startDate) >= 0 && match.getDate().compareTo(topDate) <= 0;
    }

    private int getScore(Map<LocalDate, Integer> localDateGuessesCountMap, LocalDate date) {
        return localDateGuessesCountMap.getOrDefault(date, 7);
    }

    private boolean hasStarted() {
        return this.getState()
                   .hasStarted();
    }
}
