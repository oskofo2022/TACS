package domain.files;

import domain.errors.runtime.FileNotFoundRuntimeException;
import domain.persistence.entities.enums.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileLinesStreamerTest {

    private FileLinesStreamer fileLinesStreamer;

    @BeforeEach
    void setUp() {
        this.fileLinesStreamer = new FileLinesStreamer();
    }

    @Test
    void list() {
        assertDoesNotThrow(() -> this.fileLinesStreamer.list(Language.ENGLISH.getPathWordsFile()).close());
    }

    @Test
    void listFileNotFound() {
        final var fileNotFoundRuntimeException = assertThrows(FileNotFoundRuntimeException.class, () -> this.fileLinesStreamer.list("/file.txt"));
        assertEquals("El path /file.txt no posee el archivo buscado", fileNotFoundRuntimeException.getMessage());
    }
}