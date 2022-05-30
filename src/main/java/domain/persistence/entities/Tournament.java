package domain.persistence.entities;

import domain.errors.runtime.DuplicateEntityFoundRuntimeException;
import domain.errors.runtime.TournamentUnauthorizedUserActionRuntimeException;
import domain.persistence.constants.ColumnConstants;
import domain.persistence.constants.TableConstants;
import domain.persistence.constants.TypeConstants;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.responses.gets.lists.ResponseGetListTournamentPositionResult;
import domain.security.users.WordleUser;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = TableConstants.Names.TOURNAMENTS)
public class Tournament {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Type(type = TypeConstants.UUID)
    private UUID id;

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

    @ManyToMany
    @JoinTable(
            name = TableConstants.Names.INSCRIPTIONS,
            joinColumns = { @JoinColumn(name = ColumnConstants.Names.TOURNAMENT_ID) },
            inverseJoinColumns = { @JoinColumn(name = ColumnConstants.Names.USER_ID) }
    )
    private List<User> players;

    @ManyToOne(optional = false)
    @JoinColumn(name = ColumnConstants.Names.USER_CREATOR_ID)
    private User userCreator;

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

    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    public void inscribe(User user) {
        this.getState()
            .validateInscriptionCreation();

        if (this.players.stream()
                        .anyMatch(p -> p.equals(user))) {
            throw new DuplicateEntityFoundRuntimeException("Inscription");
        }

        this.players.add(user);
    }

    public void validateAuthority(WordleUser wordleUser) {
        if (!wordleUser.getId().equals(this.userCreator.getId())) {
            throw new TournamentUnauthorizedUserActionRuntimeException();
        }
    }

    public List<ResponseGetListTournamentPositionResult> listPositions() {
        if (!this.hasStarted()) {
            return Collections.emptyList();
        }

        return this.players.stream()
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

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
