package domain.requests.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.persistence.entities.Match;
import domain.persistence.entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestPostUserMatchToday {
    @NotNull
    @Size(min = 1)
    private ArrayList<RequestPostUserMatchTodayResult> results;

    public ArrayList<RequestPostUserMatchTodayResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<RequestPostUserMatchTodayResult> results) {
        this.results = results;
    }

    @JsonIgnore
    public boolean hasLanguage(Match match) {
        return this.results.stream().anyMatch(rpumtr -> rpumtr.getLanguage() == match.getLanguage());
    }

    @JsonIgnore
    public List<Match> listMatches(User user) {
        return this.results.stream()
                   .map(rpumtr -> this.createMatch(rpumtr, user))
                   .toList();
    }


    private Match createMatch(RequestPostUserMatchTodayResult requestPostUserMatchTodayResult, User user) {
        final var match = new Match();
        match.setDate(LocalDate.now());
        match.setUser(user);
        match.setLanguage(requestPostUserMatchTodayResult.getLanguage());
        match.setGuessesCount(requestPostUserMatchTodayResult.getGuessesCount());

        return match;
    }
}
