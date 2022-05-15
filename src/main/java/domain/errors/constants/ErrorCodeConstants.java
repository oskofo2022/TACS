package domain.errors.constants;

public class ErrorCodeConstants {
    public static final String SEMANTICALLY_INCORRECT_REQUEST = "SEMANTICALLY_INCORRECT_REQUEST";
    public static final String UNHANDLED_EXCEPTION = "UNHANDLED_EXCEPTION";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String INVALID_TOURNAMENT_INSCRIPTION = "INVALID_TOURNAMENT_INSCRIPTION";
    public static final String TOURNAMENT_UNAUTHORIZED_USER_ACTION = "TOURNAMENT_UNAUTHORIZED_USER_ACTION";


    private static final String ENTITY_NOT_FOUND = "%s_NOT_FOUND";
    private static final String DUPLICATE_ENTITY_FOUND = "DUPLICATE_%s_FOUND";

    public static <T> String getEntityNotFound(Class<T> specificClass) {
        return ENTITY_NOT_FOUND.formatted(specificClass.getSimpleName().toUpperCase());
    }

    public static <T> String getDuplicateEntityFound(Class<T> specificClass) {
        return DUPLICATE_ENTITY_FOUND.formatted(specificClass.getSimpleName().toUpperCase());
    }
}
