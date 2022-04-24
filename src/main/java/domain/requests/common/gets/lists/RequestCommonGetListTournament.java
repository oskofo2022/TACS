package domain.requests.common.gets.lists;

import domain.persistence.entities.enums.Language;
import domain.requests.gets.lists.RequestGetPagedList;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RequestCommonGetListTournament extends RequestGetPagedList {
    private long idTournament;
    private String name;
    private Language language;

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

    @Override
    protected void addRestrictions() {
    }
}
