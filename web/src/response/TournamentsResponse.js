import {PagedResponse} from "./PagedResponse";

class Tournament {
    constructor(id, name, language, startDate, endDate, tournamentState, visibility) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.beginDate = startDate;
        this.endDate = endDate;
        this.tournamentState = tournamentState;
        this.visibility = visibility;
    }
}

export class TournamentsResponse extends PagedResponse {
    constructor(p) {
        super(p);
        this.pageItems = this.pageItems.map(i =>
            new Tournament(
                i.id,
                i.Name,
                i.language,
                i.startDate,
                i.endDate,
                i.tournamentState,
                i.visibility
            ));
    }
}