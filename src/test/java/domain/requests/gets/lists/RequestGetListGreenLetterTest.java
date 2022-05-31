package domain.requests.gets.lists;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestGetListGreenLetterTest {
    @Test
    void isValid() {
        final var requestGetListGreenLetter = new RequestGetListGreenLetter(5, 'a');
        assertTrue(requestGetListGreenLetter.isValid("banana"));
    }

    @Test
    void isNotValidOutOfRangeLetterPosition() {
        final var requestGetListGreenLetter = new RequestGetListGreenLetter(6, 'a');
        assertFalse(requestGetListGreenLetter.isValid("banana"));
    }
}