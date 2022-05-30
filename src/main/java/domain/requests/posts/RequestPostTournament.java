package domain.requests.posts;

import domain.interfaces.LocalDateInterval;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.Visibility;
import domain.validators.ConsistentLocalDateInterval;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ConsistentLocalDateInterval
public class RequestPostTournament implements LocalDateInterval {
    @Size(min = 4, max = 60)
    @NotNull
    private String name;

    @NotNull
    private Language language;

    @NotNull
    private Visibility visibility;

    @FutureOrPresent
    private LocalDate startDate;

    private LocalDate endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
