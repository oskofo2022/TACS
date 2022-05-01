package domain.errors.constants;

import javassist.bytecode.analysis.Type;

public class ErrorCodeConstants {
    public static final String MISMATCHED_WORD_GAME_LENGTH = "MISMATCHED_WORD_GAME_LENGTH";
    public static final String SEMANTICALLY_INCORRECT_REQUEST = "SEMANTICALLY_INCORRECT_REQUEST";
    public static final String UNHANDLED_EXCEPTION = "UNHANDLED_EXCEPTION";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String INVALID_TOURNAMENT_INSCRIPTION = "INVALID_TOURNAMENT_INSCRIPTION";

    private static final String ENTITY_NOT_FOUND = "%s_NOT_FOUND";

    public static <T> String getEntityNotFound(Class<T> specificClass) {
        return ENTITY_NOT_FOUND.formatted(specificClass.getSimpleName().toUpperCase());
    }
}
