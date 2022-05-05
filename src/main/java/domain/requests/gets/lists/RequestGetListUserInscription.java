package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import constants.RSQLConstants;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

public class RequestGetListUserInscription extends RequestGetPagedList {
    @JsonIgnore
    private long userId;

    private Long tournamentId;

    private String tournamentName;

    private Visibility tournamentVisibility;

    private Language tournamentLanguage;

    private TournamentState tournamentState;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentBottomStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentTopStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentBottomEndDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentTopEndDate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Visibility getTournamentVisibility() {
        return tournamentVisibility;
    }

    public void setTournamentVisibility(Visibility tournamentVisibility) {
        this.tournamentVisibility = tournamentVisibility;
    }

    public Language getTournamentLanguage() {
        return tournamentLanguage;
    }

    public void setTournamentLanguage(Language tournamentLanguage) {
        this.tournamentLanguage = tournamentLanguage;
    }

    public TournamentState getTournamentState() {
        return tournamentState;
    }

    public void setTournamentState(TournamentState tournamentState) {
        this.tournamentState = tournamentState;
    }

    public LocalDate getTournamentBottomStartDate() {
        return tournamentBottomStartDate;
    }

    public void setTournamentBottomStartDate(LocalDate tournamentBottomStartDate) {
        this.tournamentBottomStartDate = tournamentBottomStartDate;
    }

    public LocalDate getTournamentTopStartDate() {
        return tournamentTopStartDate;
    }

    public void setTournamentTopStartDate(LocalDate tournamentTopStartDate) {
        this.tournamentTopStartDate = tournamentTopStartDate;
    }

    public LocalDate getTournamentBottomEndDate() {
        return tournamentBottomEndDate;
    }

    public void setTournamentBottomEndDate(LocalDate tournamentBottomEndDate) {
        this.tournamentBottomEndDate = tournamentBottomEndDate;
    }

    public LocalDate getTournamentTopEndDate() {
        return tournamentTopEndDate;
    }

    public void setTournamentTopEndDate(LocalDate tournamentTopEndDate) {
        this.tournamentTopEndDate = tournamentTopEndDate;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    protected void addRestrictions() {
        this.addRestriction(RSQLConstants.Filters.getEqual("user.id"), this.userId);
        this.addRestriction(RSQLConstants.Filters.getLike("tournament.name"), this.tournamentName);
        this.addRestriction(RSQLConstants.Filters.getGreaterThanEqual("tournament.endDate"), this.tournamentBottomEndDate);
        this.addRestriction(RSQLConstants.Filters.getLowerThan("tournament.endDate"), Optional.ofNullable(this.tournamentTopEndDate).map(d -> d.plusDays(1)).orElse(null));
        this.addRestriction(RSQLConstants.Filters.getGreaterThanEqual("tournament.startDate"), this.tournamentBottomStartDate);
        this.addRestriction(RSQLConstants.Filters.getLowerThan("tournament.startDate"), Optional.ofNullable(this.tournamentTopStartDate).map(d -> d.plusDays(1)).orElse(null));
        this.addRestriction(RSQLConstants.Filters.getEqual("tournament.id"), this.tournamentId);
        this.addRestriction(RSQLConstants.Filters.getEqual("tournament.state"), this.tournamentState);
        this.addRestriction(RSQLConstants.Filters.getEqual("tournament.language"), this.tournamentLanguage);
        this.addRestriction(RSQLConstants.Filters.getEqual("tournament.visibility"), this.tournamentVisibility);
    }

    @Override
    protected String defaultSortBy() {
        return "tournament.name";
    }
}
