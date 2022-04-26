package domain.requests.posts;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

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
}
