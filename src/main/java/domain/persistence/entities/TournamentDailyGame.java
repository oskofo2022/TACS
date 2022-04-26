package domain.persistence.entities;

import domain.persistence.constants.ColumnConstants;
import domain.persistence.constants.TableConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = TableConstants.Names.TOURNAMENTS_DAILY_GAMES)
public class TournamentDailyGame {
    @EmbeddedId
    private TournamentGameIdentifier identifier;

    @NotNull
    private LocalDate lifespanDate;

    @ManyToOne(optional = false)
    @MapsId(ColumnConstants.Names.TOURNAMENT_ID)
    @JoinColumn(name = ColumnConstants.Names.TOURNAMENT_ID, referencedColumnName = ColumnConstants.Names.ID)
    private Tournament tournament;

    @ManyToOne(optional = false)
    @MapsId(ColumnConstants.Names.GAME_ID)
    @JoinColumn(name = ColumnConstants.Names.GAME_ID, referencedColumnName = ColumnConstants.Names.ID)
    private Game game;

    public TournamentGameIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(TournamentGameIdentifier identifier) {
        this.identifier = identifier;
    }

    public LocalDate getLifespanDate() {
        return lifespanDate;
    }

    public void setLifespanDate(LocalDate lifespanDate) {
        this.lifespanDate = lifespanDate;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
