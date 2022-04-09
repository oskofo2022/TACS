package domain.requests.gets.lists;

import domain.repositories.entities.Language;

public class RequestGetListUserInscription extends RequestGetPagedList {
    private String tournamentName;
    private Language tournamentLanguage;

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Language getTournamentLanguage() {
        return tournamentLanguage;
    }

    public void setTournamentLanguage(Language tournamentLanguage) {
        this.tournamentLanguage = tournamentLanguage;
    }
}
