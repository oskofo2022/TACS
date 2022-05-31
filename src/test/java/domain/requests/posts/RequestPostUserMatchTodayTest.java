package domain.requests.posts;

import domain.persistence.entities.Match;
import domain.persistence.entities.User;
import domain.persistence.entities.enums.Language;
import domain.requests.RequestAnnotationTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestPostUserMatchTodayTest extends RequestAnnotationTest<RequestPostUserMatchToday> {

    @Test
    public void resultsNotSet() {
        this.request.setResults(null);

        this.invalid("results", "NotNull");
    }

    @Test
    public void resultsEmpty() {
        this.request.setResults(Collections.emptyList());

        this.invalid("results", "Size");
    }

    @Test
    void hasLanguage() {
        final var match = new Match();
        match.setLanguage(Language.ENGLISH);

        final var firstRequestPostUserMatchTodayResult = new RequestPostUserMatchTodayResult();
        firstRequestPostUserMatchTodayResult.setLanguage(Language.SPANISH);

        final var secondRequestPostUserMatchTodayResult = new RequestPostUserMatchTodayResult();
        secondRequestPostUserMatchTodayResult.setLanguage(Language.ENGLISH);

        this.request.setResults(List.of(firstRequestPostUserMatchTodayResult, secondRequestPostUserMatchTodayResult));

        assertTrue(this.request.hasLanguage(match));
    }

    @Test
    void listMatches() {
        final var user = new User();

        final var firstRequestPostUserMatchTodayResult = new RequestPostUserMatchTodayResult();
        firstRequestPostUserMatchTodayResult.setLanguage(Language.SPANISH);
        firstRequestPostUserMatchTodayResult.setGuessesCount(2);

        final var secondRequestPostUserMatchTodayResult = new RequestPostUserMatchTodayResult();
        secondRequestPostUserMatchTodayResult.setLanguage(Language.ENGLISH);
        secondRequestPostUserMatchTodayResult.setGuessesCount(5);

        final var requestsPostUserMatchTodayResult = List.of(firstRequestPostUserMatchTodayResult, secondRequestPostUserMatchTodayResult);

        this.request.setResults(requestsPostUserMatchTodayResult);

        final var matches = this.request.listMatches(user);

        for (int index = 0; index < matches.size(); index++) {
            assertEquals(requestsPostUserMatchTodayResult.get(index).getLanguage(), matches.get(index).getLanguage());
            assertEquals(requestsPostUserMatchTodayResult.get(index).getGuessesCount(), matches.get(index).getGuessesCount());
            assertEquals(user, matches.get(index).getUser());
            assertEquals(LocalDate.now(), matches.get(index).getDate());

        }
     }

    @Override
    protected RequestPostUserMatchToday createValidRequest() {
        final var requestPostUserMatchToday = new RequestPostUserMatchToday();
        requestPostUserMatchToday.setResults(List.of(new RequestPostUserMatchTodayResult()));

        return requestPostUserMatchToday;
    }
}