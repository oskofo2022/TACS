package domain.requests.common.gets.lists;

import constants.RSQLConstants;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.requests.gets.lists.RequestGetPagedList;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class RequestCommonGetListTournament extends RequestGetPagedList {
    private UUID id;
    private String name;
    private Language language;
    private TournamentState state;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentBottomStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentTopStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentBottomEndDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentTopEndDate;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Language getLanguage() {return language;}
    public void setLanguage(Language language) {this.language = language;}

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

    public TournamentState getState() {
        return state;
    }

    public void setState(TournamentState state) {
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    protected String defaultSortBy() {
        return "name";
    }

    @Override
    protected void addRestrictions() {
        this.addRestriction(RSQLConstants.Filters.getEqual("id"), this.id);
        this.addRestriction(RSQLConstants.Filters.getLike("name"), this.name);
        this.addRestriction(RSQLConstants.Filters.getGreaterThanEqual("endDate"), this.tournamentBottomEndDate);
        this.addRestriction(RSQLConstants.Filters.getLowerThan("endDate"), Optional.ofNullable(this.tournamentTopEndDate).map(d -> d.plusDays(1)).orElse(null));
        this.addRestriction(RSQLConstants.Filters.getGreaterThanEqual("startDate"), this.tournamentBottomStartDate);
        this.addRestriction(RSQLConstants.Filters.getLowerThan("startDate"), Optional.ofNullable(this.tournamentTopStartDate).map(d -> d.plusDays(1)).orElse(null));
        this.addRestriction(RSQLConstants.Filters.getEqual("state"), this.state);
        this.addRestriction(RSQLConstants.Filters.getEqual("language"), this.language);
    }
}
