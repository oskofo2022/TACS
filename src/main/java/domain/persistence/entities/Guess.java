package domain.persistence.entities;

import domain.persistence.constants.ColumnConstants;
import domain.persistence.constants.TableConstants;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = TableConstants.Names.GUESSES)
public class Guess {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotBlank
    @Size(max = 60)
    private String word;

    @ManyToOne(optional = false)
    @JoinColumn(name = ColumnConstants.Names.GAME_ID, referencedColumnName = ColumnConstants.Names.ID)
    private Game game;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
