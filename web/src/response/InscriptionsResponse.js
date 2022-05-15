import {PagedResponse} from "./PagedResponse";
import {Tournament} from "./TournamentsResponse";

class Inscription extends Tournament{
    constructor(...params) {
        super(...params);
        this.positionURL = '/mis-torneos/' + this.id + '/positions';
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