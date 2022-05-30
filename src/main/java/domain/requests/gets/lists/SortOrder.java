package domain.requests.gets.lists;

import java.util.Comparator;

/// Is expected to use an ascending comparator for all the sorting implementations
public enum SortOrder {
    ASCENDING {
        @Override
        public <T> Comparator<T> getComparator(Comparator<T> comparator) {
            return comparator;
        }
    },
    DESCENDING {
        @Override
        public <T> Comparator<T> getComparator(Comparator<T> comparator) {
            return comparator.reversed();
        }
    };

    public abstract <T> Comparator<T> getComparator(Comparator<T> comparator);
}
