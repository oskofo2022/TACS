package domain.errors.constants;

public class ErrorMessageConstants {
    public static final String INVALID_CREDENTIALS = "Las credenciales ingresadas son inválidas";
    public static final String UNHANDLED_EXCEPTION = "Ocurrió un error al intentar procesar su petición. Intentelo nuevamente más tarde.";
    private static final String INVALID_FIELD = "%s: %s.";
    private static final String ENTITY_NOT_FOUND = "La entidad %s no pudo ser encontrada";

    public static String getInvalidField(String field, String error) {
        return INVALID_FIELD.formatted(field, error);
    }
    public static <T> String getEntityNotFound(Class<T> specificClass) {
        return ENTITY_NOT_FOUND.formatted(specificClass.getName());
    }
}
