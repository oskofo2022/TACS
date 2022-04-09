package domain.requests.gets.lists;

import domain.repositories.entities.Language;
import domain.repositories.entities.TournamentState;
import domain.repositories.entities.Visibility;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RequestGetListUserInscription extends RequestGetPagedList {
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
}
