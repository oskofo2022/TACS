package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.TournamentState;
import domain.persistence.entities.enums.Visibility;
import domain.validators.RegexSortBy;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@RegexSortBy(allowedValues = { "name", "state", "language", "id", "startDate", "endDate", "visibility" })
public class RequestGetListTournamentPosition extends RequestGetListOnMemoryPagedList<Tournament> {

    private String tournamentName;
    private TournamentState tournamentState;
    private Visibility tournamentVisibility;
    private Language tournamentLanguage;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentBottomStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentTopStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentBottomEndDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tournamentTopEndDate;

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
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

    @JsonIgnore
    @Override
    public boolean isValid(Tournament tournament) {
        return (this.tournamentName == null || this.tournamentName.isBlank() || tournament.getName().contains(this.tournamentName))
               && (this.tournamentState == null || tournament.getState() == this.tournamentState)
               && (this.tournamentBottomEndDate == null || this.tournamentBottomEndDate.compareTo(tournament.getEndDate()) <= 0)
               && (this.tournamentTopEndDate == null || this.tournamentTopEndDate.compareTo(tournament.getEndDate()) >= 0)
               && (this.tournamentBottomStartDate == null || this.tournamentBottomStartDate.compareTo(tournament.getStartDate()) <= 0)
               && (this.tournamentTopStartDate == null || this.tournamentTopStartDate.compareTo(tournament.getStartDate()) >= 0)
               && (this.tournamentVisibility == null || tournament.getVisibility() == this.tournamentVisibility)
               && (this.tournamentLanguage == null || tournament.getLanguage() == this.tournamentLanguage);

    }

    @JsonIgnore
    @Override
    public Map<String, Comparator<Tournament>> getComparatorMap() {
        return new HashMap<>() {
            {
                put("name", Comparator.comparing(Tournament::getName));
                put("state", Comparator.comparing(Tournament::getState));
                put("language", Comparator.comparing(Tournament::getLanguage));
                put("id", Comparator.comparing(Tournament::getId));
                put("startDate", Comparator.comparing(Tournament::getStartDate));
                put("endDate", Comparator.comparing(Tournament::getEndDate));
                put("visibility", Comparator.comparing(Tournament::getVisibility));
            }
        };
    }

    @Override
    protected String defaultSortBy() {
        return "name";
    }
}
