package domain.requests.gets.lists;

import domain.responses.gets.lists.ResponseGetListGameHelp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class RequestGetListGameHelpTest extends RequestGetListOnMemoryPagedListTest<String, RequestGetListGameHelp> {

    @Test
    public void getBadLettersAsLowercase() {
        this.request.setBadLetters("CVZXA");

        assertEquals("cvzxa", this.request.getBadLetters());
    }

    @Test
    public void getGoodLettersAsLowercase() {
        this.request.setGoodLetters("ASVSA");

        assertEquals("asvsa", this.request.getGoodLetters());
    }

    @Test
    public void getGreenLettersAsLowercase() {
        this.request.setGreenLetters("-RAR");

        assertEquals("-rar", this.request.getGreenLetters());
    }

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
    public void isValidByBadLetters() {
        this.request.setBadLetters("ap");
        final var validWords = Arrays.asList("south", "length", "color", "omicron", "light");

        this.assertion(validWords, Assertions::assertTrue);
    }

    @Test
    public void isNotValidByBadLetters() {
        this.request.setBadLetters("ap");
        final var invalidWords = Arrays.asList("apple", "people", "polite", "brawler", "operation");

        this.assertion(invalidWords, Assertions::assertFalse);
    }

    @Test
    public void isValidByGoodLetters() {
        this.request.setGoodLetters("ap");
        final var validWords = Arrays.asList("apple", "appraise", "apology", "payment", "operation");

        this.assertion(validWords, Assertions::assertTrue);
    }

    @Test
    public void isNotValidByGoodLetters() {
        this.request.setGoodLetters("ap");
        final var invalidWords = Arrays.asList("polite", "amaze", "process", "south", "around");

        this.assertion(invalidWords, Assertions::assertFalse);
    }

    @Test
    public void isValidByGreenLetters() {
        this.request.setGreenLetters("a-p");
        final var validWords = Arrays.asList("apple", "appraise", "app", "ampersand", "applause");

        this.assertion(validWords, Assertions::assertTrue);
    }

    @Test
    public void isNotValidByGreenLetters() {
        this.request.setGreenLetters("a-p");
        final var invalidWords = Arrays.asList("praise", "payment", "operation", "apology", "antelope");

        this.assertion(invalidWords, Assertions::assertFalse);
    }

    @Test
    public void isValid() {
        this.request.setGoodLetters("pp");
        this.request.setBadLetters("mu");
        this.request.setGreenLetters("a-p");
        final var validWords = Arrays.asList("apple", "app", "appraise");

        this.assertion(validWords, Assertions::assertTrue);
    }

    @Test
    public void sortByWord() {
        this.request.setSortBy("word");

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
        assertEquals("word", this.request.defaultSortBy());
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

    private void assertion(List<String> words, Consumer<Boolean> assertContext) {
        words.forEach(w -> assertContext.accept(this.request.isValid(w)));
    }
}