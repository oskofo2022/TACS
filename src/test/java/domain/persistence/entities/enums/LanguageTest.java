package domain.persistence.entities.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    @Test
    void getPathWordsFile() {
        for (var language: Language.values()) {
            assertEquals("/static/games/words/" + language.name().toLowerCase() + ".list", language.getPathWordsFile());
        }
    }
}