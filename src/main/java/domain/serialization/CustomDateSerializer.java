package domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDateSerializer extends JsonSerializer<LocalDate> {
    public static final DateTimeFormatter FORMATTER_1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATTER_2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(FORMATTER_1.format(value));
        }
    }
}
