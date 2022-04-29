package domain.persistence.entities.enums;

public enum Language {
    ENGLISH,
    SPANISH;

    public String getPathWordsFile() {
        return "/static/games/words/" + this.name().toLowerCase() + ".list";
    }
}
