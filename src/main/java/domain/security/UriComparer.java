package domain.security;

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
            return expectedUri.equals(actualUri);
        }
    };

    public abstract boolean match(String expectedUri, String actualUri);
}
