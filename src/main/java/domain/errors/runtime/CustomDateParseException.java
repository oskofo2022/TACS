package domain.errors.runtime;

public class CustomDateParseException extends RuntimeException{

    private final String parsedData;

    public CustomDateParseException(String message, String parsedData) {
        super(message);
        this.parsedData = parsedData;
    }

    public String getParsedData() {
        return parsedData;
    }
}
