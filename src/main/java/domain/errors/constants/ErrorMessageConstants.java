package domain.errors.constants;

public class ErrorMessageConstants {
    public static final String INVALID_CREDENTIALS = "Las credenciales ingresadas son inválidas";
    public static final String UNHANDLED_EXCEPTION = "Ocurrió un error al intentar procesar su petición. Intentelo nuevamente más tarde.";
    public static final String INVALID_TOURNAMENT_INSCRIPTION = "La entidad torneo no se encuentra disponible para nuevas inscripciones";
    public static final String TOURNAMENT_UNAUTHORIZED_USER_ACTION = "No está autorizado para realizar la acción ingresada";


    private static final String INVALID_REQUEST = "%s: %s.";
    private static final String ENTITY_NOT_FOUND = "La entidad %s no pudo ser encontrada";
    private static final String INVALID_FILE_PATH = "El path %s no posee el archivo buscado";
    private static final String DUPLICATE_ENTITY_FOUND = "La entidad %s que intenta crear ya se encuentra presente";

    public static class Entities {
        public static class Names {
            public static final String USER = "usuario";
            public static final String INSCRIPTION = "inscripción";
            public static final String TOURNAMENT = "torneo";
            public static final String MATCH = "partido";
        }
    }

    public static String getInvalidRequest(String wrapperName, String error) {
        return INVALID_REQUEST.formatted(wrapperName, error);
    }
    public static String getEntityNotFound(String entityName) {
        return ENTITY_NOT_FOUND.formatted(entityName);
    }
    public static String getInvalidFilePath(String filePath) {
        return INVALID_FILE_PATH.formatted(filePath);
    }
    public static <T> String getDuplicateEntityFound(Class<T> specificClass) {
        return getDuplicateEntityFound(specificClass.getSimpleName());
    }

    public static String getDuplicateEntityFound(String duplicateName) {
        return DUPLICATE_ENTITY_FOUND.formatted(duplicateName);
    }
}
