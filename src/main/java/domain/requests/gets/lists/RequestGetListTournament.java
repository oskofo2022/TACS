package domain.requests.gets.lists;

import domain.repositories.entities.Language;
import domain.repositories.entities.Visibility;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RequestGetListTournament extends RequestGetPagedList {
    private long idTournament;
    private String name;
    private Language language;
    private Visibility visibility;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    public long getIdTournament() { return idTournament; }
    public void setIdTournament(long idTournament) { this.idTournament = idTournament;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Language getLanguage() {return language;}
    public void setLanguage(Language language) {this.language = language;}

    public Visibility getVisibility() {
        return visibility;
    }
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
