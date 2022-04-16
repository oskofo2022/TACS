package domain.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import domain.errors.runtime.CustomDateParseException;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class CustomDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException, CustomDateParseException {
        String dateAsString = jsonparser.getText();
        return (LocalDate) Optional.empty()
                .or(() -> Optional.ofNullable(tryParse(CustomDateSerializer.FORMATTER_1, dateAsString)))
                .or(() -> Optional.ofNullable(tryParse(CustomDateSerializer.FORMATTER_2, dateAsString)))
                .orElseThrow(() -> new CustomDateParseException("Text '" + dateAsString + "' could not be parsed by any of the available parsers.", dateAsString));
    }

    private LocalDate tryParse(@NotNull DateTimeFormatter formatter, String dateAsString) {
        try {
            return LocalDate.from(formatter.parse(dateAsString));
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
