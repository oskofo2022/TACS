package domain.requests.posts;

import domain.errors.runtime.MismatchedGameWordLengthError;
import domain.repositories.entities.Game;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RequestPostUserInscriptionGameGuess {
    @Size(max = 60)
    @NotBlank
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
