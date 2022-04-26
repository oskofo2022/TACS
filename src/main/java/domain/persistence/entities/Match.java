package domain.persistence.entities;

import domain.persistence.constants.ColumnConstants;
import domain.persistence.constants.TableConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = TableConstants.Names.MATCHES)
public class Match {
    @EmbeddedId
    private MatchIdentifier identifier;

    @ManyToOne(optional = false)
    @MapsId(ColumnConstants.Names.USER_ID)
    @JoinColumn(name = ColumnConstants.Names.USER_ID, referencedColumnName = ColumnConstants.Names.ID)
    private User user;

    @NotNull
    private int guessesCount;

    @ManyToOne(optional = false)
    @JoinColumns( {
            @JoinColumn(name = ColumnConstants.Names.TournamentsDailyGames.TOURNAMENT_ID, referencedColumnName = ColumnConstants.Names.TOURNAMENT_ID),
            @JoinColumn(name = ColumnConstants.Names.TournamentsDailyGames.GAME_ID, referencedColumnName = ColumnConstants.Names.GAME_ID)
    })
    private TournamentDailyGame tournamentDailyGame;

    public MatchIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(MatchIdentifier identifier) {
        this.identifier = identifier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getGuessesCount() {
        return guessesCount;
    }

    public void setGuessesCount(int guessesCount) {
        this.guessesCount = guessesCount;
    }

    public TournamentDailyGame getTournamentDailyGame() {
        return tournamentDailyGame;
    }

    public void setTournamentDailyGame(TournamentDailyGame tournamentDailyGame) {
        this.tournamentDailyGame = tournamentDailyGame;
    }
}
