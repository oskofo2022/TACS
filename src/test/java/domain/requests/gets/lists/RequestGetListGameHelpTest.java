package domain.requests.gets.lists;

import domain.responses.gets.lists.ResponseGetListGameHelp;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RequestGetListGameHelpTest extends RequestGetListOnMemoryPagedListTest<String, RequestGetListGameHelp> {

    @Test
    public void badLettersOverSized() {
        this.request.setBadLetters("a".repeat(21));

        this.invalid("badLetters", "Size");
    }

    @Test
    public void goodLettersOverSized() {
        this.request.setGoodLetters("a".repeat(21));

        this.invalid("goodLetters", "Size");
    }

    @Test
    public void greenLettersOverSized() {
        this.request.setGoodLetters("a".repeat(21));

        this.invalid("goodLetters", "Size");
    }

    @Test
    public void isValid() {
        this.request.setBadLetters("aw");
        this.request.setGoodLetters("el");

        assertTrue(this.request.isValid("eel"));
    }

    @Test
    public void isNotValid() {
        this.request.setBadLetters("ai");
        this.request.setGoodLetters("h");


        assertFalse(this.request.isValid("fish"));
    }

    @Test
    public void sortByIdentity() {
        this.request.setSortBy("identity");

        this.valid();
    }

    @Test
    public void sortByInvalidValue() {
        this.request.setSortBy("invalid");

        this.invalid("", "RegexSortBy");
    }

    @Override
    protected RequestGetListGameHelp createValidRequest() {
        return new RequestGetListGameHelp();
    }

    @Test
    @Override
    public void defaultSortBy() {
        assertEquals("identity", this.request.defaultSortBy());
    }

    @Test
    @Override
    public void paginate() {
        this.request.setPage(1);
        this.request.setPageSize(3);
        this.request.setSortOrder(SortOrder.DESCENDING);
        this.request.setBadLetters("b");

        final var words = new ArrayList<String>() {
            {
                add("line");
                add("some");
                add("bone");
                add("lime");
                add("cors");
            }
        };

        final var responseGetPagedList = this.request.paginate(words::stream, ResponseGetListGameHelp::new);

        assertEquals(2, responseGetPagedList.pageCount());
        assertEquals(4, responseGetPagedList.totalCount());
        assertEquals(3, responseGetPagedList.pageItems().size());

        final var responsesGetListGameHelp = responseGetPagedList.pageItems();
        assertEquals("some", responsesGetListGameHelp.get(0).word());
        assertEquals("line", responsesGetListGameHelp.get(1).word());
        assertEquals("lime", responsesGetListGameHelp.get(2).word());
    }
}