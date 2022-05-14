import {PagedResponse} from "./PagedResponse";

class Inscription {
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

export class InscriptionsResponse extends PagedResponse {
    constructor(p) {
        super(p);
        this.pageItems = this.pageItems.map(i => {
            const t = i.tournament;
            return new Inscription(
                    t.id,
                    t.Name,
                    t.language,
                    t.startDate,
                    t.endDate,
                    t.tournamentState,
                    t.visibility
                )
        });
    }
}