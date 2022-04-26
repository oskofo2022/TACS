package domain.requests.posts;

import domain.persistence.entities.enums.Language;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RequestPostUserMatchTodayResult {
    @NotNull
    private Language language;

    @Min(1)
    @Max(7)
    private int guessesCount;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getGuessesCount() {
        return guessesCount;
    }

    public void setGuessesCount(int guessesCount) {
        this.guessesCount = guessesCount;
    }
}
