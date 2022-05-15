package domain.errors.constants;

public class ErrorMessageConstants {
    public static final String INVALID_CREDENTIALS = "Las credenciales ingresadas son inválidas";
    public static final String UNHANDLED_EXCEPTION = "Ocurrió un error al intentar procesar su petición. Intentelo nuevamente más tarde.";
    public static final String INVALID_TOURNAMENT_INSCRIPTION = "El torneo no se encuentra disponible para nuevas inscripciones";
    public static final String TOURNAMENT_UNAUTHORIZED_USER_ACTION = "No está autorizado para realizar la acción intentada";


    private static final String INVALID_FIELD = "%s: %s.";
    private static final String ENTITY_NOT_FOUND = "La entidad %s no pudo ser encontrada";
    private static final String INVALID_FILE_PATH = "El path %s no posee el archivo buscado";
    private static final String DUPLICATE_ENTITY_FOUND = "La entidad %s que intenta crear ya se encuentra presente";

    public static String getInvalidField(String field, String error) {
        return INVALID_FIELD.formatted(field, error);
    }
    public static <T> String getEntityNotFound(Class<T> specificClass) {
        return ENTITY_NOT_FOUND.formatted(specificClass.getSimpleName());
    }
    public static <T> String getInvalidFilePath(String filePath) {
        return INVALID_FILE_PATH.formatted(filePath);
    }
    public static <T> String getDuplicateEntityFound(Class<T> specificClass) {
        return DUPLICATE_ENTITY_FOUND.formatted(specificClass.getSimpleName());
    }
}
