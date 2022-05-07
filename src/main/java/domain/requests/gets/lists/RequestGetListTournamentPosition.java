package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.persistence.entities.Tournament;
import domain.persistence.entities.enums.TournamentState;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;

public class RequestGetListTournamentPosition extends RequestGetListOnMemoryPagedList<Tournament> {

    private String tournamentName;
    private TournamentState tournamentState;

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

    @JsonIgnore
    public boolean isValid(Tournament tournament) {
        return (this.tournamentName == null || this.tournamentName.isBlank() || tournament.getName().contains(this.tournamentName))
               && (this.tournamentState == null || tournament.getState() == this.tournamentState)
               && (this.tournamentBottomEndDate == null || this.tournamentBottomEndDate.compareTo(tournament.getEndDate()) >= 0)
               && (this.tournamentTopEndDate == null || this.tournamentTopEndDate.compareTo(tournament.getEndDate()) <= 0)
               && (this.tournamentBottomStartDate == null || this.tournamentBottomStartDate.compareTo(tournament.getStartDate()) >= 0)
               && (this.tournamentTopStartDate == null || this.tournamentTopStartDate.compareTo(tournament.getStartDate()) <= 0);

    }

    @Override
    protected String defaultSortBy() {
        return "name";
    }
}
