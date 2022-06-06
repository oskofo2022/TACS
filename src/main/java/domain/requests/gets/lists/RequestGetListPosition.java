package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.responses.gets.lists.ResponseGetListTournamentPositionResult;
import domain.validators.RegexSortBy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@RegexSortBy(allowedValues = { "guessesCount" })
public class RequestGetListPosition extends RequestGetListOnMemoryPagedList<ResponseGetListTournamentPositionResult> {
    @Override
    protected String defaultSortBy() {
        return "guessesCount";
    }

    @JsonIgnore
    @Override
    public boolean isValid(ResponseGetListTournamentPositionResult instance) {
        return true;
    }

    @JsonIgnore
    @Override
    public Map<String, Comparator<ResponseGetListTournamentPositionResult>> getComparatorMap() {
        return new HashMap<>() {
            {
                put("guessesCount", Comparator.comparing(ResponseGetListTournamentPositionResult::guessesCount));
            }
        };
    }
}
