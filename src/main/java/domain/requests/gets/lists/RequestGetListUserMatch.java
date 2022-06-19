package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import constants.RSQLConstants;
import domain.persistence.entities.enums.Language;
import domain.validators.RegexSortBy;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RegexSortBy(allowedValues = { "date", "language" })
public class RequestGetListUserMatch extends RequestGetPagedList {
    @JsonIgnore
    private UUID userId;

    private Language language;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate bottomDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate topDate;

    @Override
    protected void addRestrictions() {
        this.addRestriction(RSQLConstants.Filters.getEqual("user.id"), this.userId);
        this.addRestriction(RSQLConstants.Filters.getEqual("language"), this.language);
        this.addRestriction(RSQLConstants.Filters.getGreaterThanEqual("date"), this.bottomDate);
        this.addRestriction(RSQLConstants.Filters.getLowerThan("date"), Optional.ofNullable(this.topDate).map(d -> d.plusDays(1)).orElse(null));
    }

    @Override
    protected String defaultSortBy() {
        return "date";
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LocalDate getBottomDate() {
        return bottomDate;
    }

    public void setBottomDate(LocalDate bottomDate) {
        this.bottomDate = bottomDate;
    }

    public LocalDate getTopDate() {
        return topDate;
    }

    public void setTopDate(LocalDate topDate) {
        this.topDate = topDate;
    }
}
