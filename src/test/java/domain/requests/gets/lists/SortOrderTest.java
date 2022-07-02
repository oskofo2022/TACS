package domain.requests.gets.lists;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class SortOrderTest {

    @Test
    public void sortOrderAscendingNext() {
        assertEquals(2, SortOrder.ASCENDING.next(1));
    }

    @Test
    public void sortOrderAscendingGetComparator() {
        final var numbers = Arrays.asList(1, 2, 3, 4, 5);
        final Comparator<Integer> naturalOrderComparator = Comparator.naturalOrder();

        final var comparator = SortOrder.ASCENDING.getComparator(naturalOrderComparator);

        final var sortedNumbers = numbers.stream()
                                                     .sorted(comparator)
                                                     .toList();

        for(var index = 0; index < numbers.size(); index++) {
            assertEquals(numbers.get(index), sortedNumbers.get(index));
        }
    }

    @Test
    public void sortOrderDescendingNext() {
        assertEquals(0, SortOrder.DESCENDING.next(1));
    }

    @Test
    public void sortOrderDescendingGetComparator() {
        final var numbers = Arrays.asList(1, 2, 3, 4, 5);
        final Comparator<Integer> naturalOrderComparator = Comparator.naturalOrder();

        final var comparator = SortOrder.DESCENDING.getComparator(naturalOrderComparator);

        final var sortedNumbers = numbers.stream()
                .sorted(comparator)
                .toList();

        for(var index = 0; index < numbers.size(); index++) {
            assertEquals(numbers.get(index), sortedNumbers.get(sortedNumbers.size() - index - 1));
        }
    }
}