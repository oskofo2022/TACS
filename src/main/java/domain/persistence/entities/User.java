package domain.persistence.entities;

import domain.persistence.constants.ColumnConstants;
import domain.persistence.constants.TableConstants;
import domain.persistence.constants.TypeConstants;
import domain.persistence.entities.enums.TournamentState;
import domain.requests.posts.RequestPostTournament;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = TableConstants.Names.USERS)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = TypeConstants.UUID)
    private UUID id;

    @Column(unique = true)
    @Size(min = 4, max = 60)
    @NotNull
    private String name;

    @Column(unique = true)
    @Size(max = 100)
    @NotNull
    private String email;

    @NotNull
    @Size(max = 80)
    private String password;

    @ManyToMany
    @JoinTable(
            name = TableConstants.Names.INSCRIPTIONS,
            joinColumns = { @JoinColumn(name = ColumnConstants.Names.USER_ID) },
            inverseJoinColumns = { @JoinColumn(name = ColumnConstants.Names.TOURNAMENT_ID) }
    )
    private List<Tournament> inscribedTournaments;

    @OneToMany
    @JoinColumn(name = ColumnConstants.Names.USER_ID)
    private List<Match> matches;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public List<Tournament> getInscribedTournaments() {
        return inscribedTournaments;
    }

    public void setInscribedTournaments(List<Tournament> inscribedTournaments) {
        this.inscribedTournaments = inscribedTournaments;
    }

    public Tournament createTournament(RequestPostTournament requestPostTournament) {
        final var tournament = new Tournament();
        tournament.setState(TournamentState.READY);
        tournament.setUserCreator(this);
        tournament.setPlayers(List.of(this));
        tournament.setName(requestPostTournament.getName());
        tournament.setEndDate(requestPostTournament.getEndDate());
        tournament.setStartDate(requestPostTournament.getStartDate());
        tournament.setLanguage(requestPostTournament.getLanguage());
        tournament.setVisibility(requestPostTournament.getVisibility());

        return tournament;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
