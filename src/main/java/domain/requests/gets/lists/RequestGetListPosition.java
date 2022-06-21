package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.persistence.entities.Tournament;
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
                put("guessesCount", Comparator.comparing(ResponseGetListTournamentPositionResult::getGuessesCount));
            }
        };
    }

    @JsonIgnore
    private long cardinal;

    @JsonIgnore
    public void setStartCardinal(Tournament tournament) {
        this.cardinal = this.getSortOrder() == SortOrder.ASCENDING ? this.getBaseAscendingCardinal() : this.getBaseDescendingCardinal(tournament);
    }

    @JsonIgnore
    public ResponseGetListTournamentPositionResult setCardinal(ResponseGetListTournamentPositionResult responseGetListTournamentPositionResult) {
        responseGetListTournamentPositionResult.setCardinal(cardinal);
        this.setNextCardinal();
        return responseGetListTournamentPositionResult;
    }

    public void setNextCardinal() {
        this.cardinal = this.getSortOrder()
                            .next(this.cardinal);
    }

    @JsonIgnore
    private long getBaseAscendingCardinal() {
        return this.countPreviousItems() + 1;
    }

    @JsonIgnore
    private long getBaseDescendingCardinal(Tournament tournament) {
        return tournament.listPositions().count() - this.countPreviousItems();
    }

    private long countPreviousItems() {
        return (long) this.getPage() * this.getPageSize();
    }
}
