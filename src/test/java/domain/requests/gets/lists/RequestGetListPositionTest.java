package domain.requests.gets.lists;

import domain.responses.gets.lists.ResponseGetListTournamentPositionResult;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

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
        assertTrue(this.request.isValid(new ResponseGetListTournamentPositionResult()));
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

        final var firstPosition = new ResponseGetListTournamentPositionResult();
        firstPosition.setName("Name 1");
        firstPosition.setGuessesCount(4);
        firstPosition.setCardinal(4);

        final var secondPosition = new ResponseGetListTournamentPositionResult();
        firstPosition.setName("Name 2");
        firstPosition.setGuessesCount(3);
        firstPosition.setCardinal(3);

        final var thirdPosition = new ResponseGetListTournamentPositionResult();
        firstPosition.setName("Name 3");
        firstPosition.setGuessesCount(2);
        firstPosition.setCardinal(2);

        final var fourthPosition = new ResponseGetListTournamentPositionResult();
        firstPosition.setName("Name 4");
        firstPosition.setGuessesCount(1);
        firstPosition.setCardinal(1);

        final var responsesGetListTournamentPositionResult = Arrays.asList(firstPosition, secondPosition, thirdPosition, fourthPosition);

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