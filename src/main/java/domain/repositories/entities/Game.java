package domain.repositories.entities;

import domain.errors.runtime.MismatchedGameWordLengthError;
import domain.requests.posts.RequestPostUserInscriptionGameGuess;
import domain.responses.gets.lists.CharProximity;
import domain.responses.common.gets.ResponseCommonGetGuessCharMatching;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.IntStream;

@Entity
public class Game {
    @Id
    private long id;

    @NotBlank
    @Size(max = 60)
    private String word;

    @NotNull
    private GameState state;

    @ManyToOne
    private Inscription inscription;

    @OneToMany
    private List<Guess> guesses;

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


    public List<ResponseCommonGetGuessCharMatching> listGuessCharsMatching(RequestPostUserInscriptionGameGuess requestPostUserInscriptionGameGuess) {
        this.validateLengthConsistency(requestPostUserInscriptionGameGuess);

        var wordGuess = requestPostUserInscriptionGameGuess.getWord();

        return IntStream.range(0, wordGuess.length())
                        .mapToObj(index -> this.getCharMatching(wordGuess.charAt(index), index))
                        .toList();
    }

    private void validateLengthConsistency(RequestPostUserInscriptionGameGuess requestPostUserInscriptionGameGuess) {
        var guessWordLength = requestPostUserInscriptionGameGuess.getWord()
                                                                      .length();
        if (guessWordLength != this.word.length()) {
            var message = "Expected length %d".formatted(this.word.length());
            throw new MismatchedGameWordLengthError(message);
        }
    }

    private ResponseCommonGetGuessCharMatching getCharMatching(char character, int index) {
        return new ResponseCommonGetGuessCharMatching(character, CharProximity.HIT.getProximity(this.word, character, index));
    }
}
