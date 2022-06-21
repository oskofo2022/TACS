package domain.requests.gets.lists;

import java.util.Comparator;

/// Is expected to use an ascending comparator for all the sorting implementations
public enum SortOrder {
    ASCENDING {
        @Override
        public <T> Comparator<T> getComparator(Comparator<T> comparator) {
            return comparator;
        }

        @Override
        public long next(long number) {
            return number + 1;
        }
    },
    DESCENDING {
        @Override
        public <T> Comparator<T> getComparator(Comparator<T> comparator) {
            return comparator.reversed();
        }

        @Override
        public long next(long number) {
            return number - 1;
        }
    };

    public abstract <T> Comparator<T> getComparator(Comparator<T> comparator);

    public abstract long next(long cardinal);
}
