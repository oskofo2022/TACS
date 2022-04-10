package domain.responses.posts;

import domain.repositories.entities.Language;
import domain.repositories.entities.Visibility;

import java.time.LocalDate;

public class ResponsePostTournament extends ResponsePostEntityCreation {

    private final String name;

    private final Language lang;

    private final Visibility visibility;

    private final LocalDate beginDate;

    private final LocalDate endDate;

    public ResponsePostTournament(long id, String name, Language lang, Visibility visibility, LocalDate beginDate, LocalDate endDate) {
        super(id);
        this.name = name;
        this.lang = lang;
        this.visibility = visibility;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public Language getLang() {
        return lang;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}

