package domain.requests.gets.lists;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestGetListGameHelpTest extends RequestGetListOnMemoryPagedListTest<RequestGetListGameHelp> {

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
    void isValid() {
        this.request.setBadLetters("aw");
        this.request.setGoodLetters("el");

        assertTrue(this.request.isValid("eel"));
    }

    @Test
    void isNotValid() {
        this.request.setBadLetters("ai");
        this.request.setGoodLetters("h");


        assertFalse(this.request.isValid("fish"));
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
}