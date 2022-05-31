package domain.requests.gets.lists;

import domain.requests.RequestAnnotationTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestGetListGameHelpTest extends RequestAnnotationTest<RequestGetListGameHelp> {

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
    void getIndexedGreenLetters() {
        this.request.setGreenLetters("--b-r");

        final var requestsGetListGreenLetter = this.request.getIndexedGreenLetters();

        assertEquals('b', requestsGetListGreenLetter.get(0).letter());
        assertEquals(2, requestsGetListGreenLetter.get(0).position());
        assertEquals('r', requestsGetListGreenLetter.get(1).letter());
        assertEquals(4, requestsGetListGreenLetter.get(1).position());
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
}