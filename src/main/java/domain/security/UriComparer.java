package domain.security;

import java.util.Objects;

public enum UriComparer {
    CONTAINS {
        @Override
        public boolean match(String expectedUri, String actualUri) {
            return actualUri.contains(expectedUri);
        }
    },
    EQUAL {
        @Override
        public boolean match(String expectedUri, String actualUri) {
            return Objects.equals(expectedUri, actualUri);
        }
    };

    public abstract boolean match(String expectedUri, String actualUri);
}
