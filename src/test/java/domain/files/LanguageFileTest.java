package domain.files;

import domain.persistence.entities.enums.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageFileTest {

    private FileLinesStreamer fileLinesStreamer;

    @BeforeEach
    void setUp() {
        this.fileLinesStreamer = new FileLinesStreamer();
    }

    @Test
    void exists() {
        final var languages = Language.values();
        for (var language : languages) {
            assertDoesNotThrow(() -> this.fileLinesStreamer.list(language.getPathWordsFile()).close(), "No se encontró ningún archivo para el lenguaje %s".formatted(language));
        }
    }
}
