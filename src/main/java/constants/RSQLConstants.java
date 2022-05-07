package constants;

public class RSQLConstants {
    public static final String AND = ";";
    public static final String OR = ",";
    private static final String PROPERTY_PATH = "{propertyPath}";

    public static class Filters {
        private static final String LIKE = "{propertyPath}=like='%s'";
        private static final String EQUAL = "{propertyPath}==%s";
        private static final String EQUAL_STRING = "{propertyPath}=='%s'";
        private static final String GREATER_THAN_EQUAL = "{propertyPath}>=%s";
        private static final String GREATER_THAN = "{propertyPath}>%s";
        private static final String LOWER_THAN_EQUAL = "{propertyPath}<=%s";
        private static final String LOWER_THAN = "{propertyPath}<%s";

        public static String getLike(String propertyPath) {
            return LIKE.replace(PROPERTY_PATH, propertyPath);
        }

        public static String getEqual(String propertyPath) {
            return EQUAL.replace(PROPERTY_PATH, propertyPath);
        }

        public static String getEqualString(String propertyPath) {
            return EQUAL_STRING.replace(PROPERTY_PATH, propertyPath);
        }

        public static String getGreaterThanEqual(String propertyPath) {
            return GREATER_THAN_EQUAL.replace(PROPERTY_PATH, propertyPath);
        }

        public static String getGreaterThan(String propertyPath) {
            return GREATER_THAN.replace(PROPERTY_PATH, propertyPath);
        }

        public static String getLowerThanEqual(String propertyPath) {
            return LOWER_THAN_EQUAL.replace(PROPERTY_PATH, propertyPath);
        }

        public static String getLowerThan(String propertyPath) {
            return LOWER_THAN.replace(PROPERTY_PATH, propertyPath);
        }
    }
}
