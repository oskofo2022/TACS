package domain.requests.posts;

import domain.persistence.entities.enums.Language;
import domain.requests.RequestAnnotationTest;
import org.junit.jupiter.api.Test;

class RequestPostUserMatchTodayResultTest extends RequestAnnotationTest<RequestPostUserMatchTodayResult> {

    @Test
    public void languageNotSet() {
        this.request.setLanguage(null);

        super.invalid("language", "NotNull");
    }

    @Test
    public void guessesCountBelowRange() {
        this.request.setGuessesCount(0);

        super.invalid("guessesCount", "Min");
    }

    @Test
    public void guessesCountOverRange() {
        this.request.setGuessesCount(8);

        super.invalid("guessesCount", "Max");
    }

    @Override
    protected RequestPostUserMatchTodayResult createValidRequest() {
        final var requestPostUserMatchTodayResult = new RequestPostUserMatchTodayResult();
        requestPostUserMatchTodayResult.setLanguage(Language.SPANISH);
        requestPostUserMatchTodayResult.setGuessesCount(2);

        return requestPostUserMatchTodayResult;
    }
}