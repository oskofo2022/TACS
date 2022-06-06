package domain.requests.gets.lists;

import domain.responses.gets.lists.ResponseGetListTournamentPositionResult;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RequestGetListPositionTest extends RequestGetListOnMemoryPagedListTest<ResponseGetListTournamentPositionResult, RequestGetListPosition> {

    @Test
    public void sortByIdentity() {
        this.request.setSortBy("guessesCount");

        this.valid();
    }

    @Test
    public void sortByInvalidValue() {
        this.request.setSortBy("invalid");

        this.invalid("", "RegexSortBy");
    }

    @Test
    public void isValid() {
        assertTrue(this.request.isValid(new ResponseGetListTournamentPositionResult("name", 7)));
    }

    @Override
    protected RequestGetListPosition createValidRequest() {
        return new RequestGetListPosition();
    }

    @Test
    @Override
    public void defaultSortBy() {
        assertEquals("guessesCount", this.request.defaultSortBy());
    }

    @Test
    @Override
    public void paginate() {
        this.request.setPage(1);
        this.request.setPageSize(3);
        this.request.setSortOrder(SortOrder.DESCENDING);

        final var responsesGetListTournamentPositionResult = new ArrayList<ResponseGetListTournamentPositionResult>() {
            {
                add(new ResponseGetListTournamentPositionResult("name 1", 4));
                add(new ResponseGetListTournamentPositionResult("name 2", 3));
                add(new ResponseGetListTournamentPositionResult("name 3", 2));
                add(new ResponseGetListTournamentPositionResult("name 4", 1));
            }
        };

        final var responseGetPagedList = this.request.paginate(responsesGetListTournamentPositionResult::stream);

        assertEquals(2, responseGetPagedList.pageCount());
        assertEquals(4, responseGetPagedList.totalCount());
        assertEquals(3, responseGetPagedList.pageItems().size());

        final var pageItems = responseGetPagedList.pageItems();

        for (int index = 0; index < pageItems.size(); index++)
        {
            assertEquals(responsesGetListTournamentPositionResult.get(index), pageItems.get(index));
        }
    }

}