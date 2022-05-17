package constants;

public class PatternConstants {
    public static final String EMAIL = ".+@.+(\\..+)+";
    public static final String AT_LEAST_ONE_NUMBER = ".*[0-9]+.*";
    public static final String AT_LEAST_ONE_LOWERCASE_LETTER = ".*[a-z]+.*";
    public static final String AT_LEAST_ONE_UPPERCASE_LETTER = ".*[A-Z]+.*";
    public static final String AT_LEAST_ONE_SPECIAL_CHARACTER = ".*[-_@,;.]+.*";
    public static final String OR_OPERATOR = "|";

    private static final String ENCASED_REGEX = "^%s$";

    public static String getEncasedRegex(String regex) {
        return ENCASED_REGEX.formatted(regex);
    }
}
