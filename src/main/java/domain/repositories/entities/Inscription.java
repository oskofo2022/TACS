package domain.repositories.entities;

import domain.repositories.constants.ColumnConstants;

import javax.persistence.*;
import java.util.List;

@Entity
public class Inscription {

    @EmbeddedId
    private InscriptionIdentifier inscriptionIdentifier;

    @MapsId(ColumnConstants.TOURNAMENT_ID)
    @ManyToOne
    private Tournament tournament;

    @MapsId(ColumnConstants.USER_ID)
    @ManyToOne
    private User user;

    @OneToMany
    private List<Game> games;

    public InscriptionIdentifier getInscriptionIdentifier() {
        return inscriptionIdentifier;
    }
    public void setInscriptionIdentifier(InscriptionIdentifier inscriptionIdentifier) {
        this.inscriptionIdentifier = inscriptionIdentifier;
    }

    public Tournament getTournament() {
        return tournament;
    }
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public List<Game> getGames() {
        return games;
    }
    public void setGames(List<Game> games) {
        this.games = games;
    }
}
