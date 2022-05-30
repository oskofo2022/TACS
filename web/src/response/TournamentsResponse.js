import {PagedResponse} from "./PagedResponse";

export class Tournament {
    constructor(id, name, language, startDate, endDate, state, visibility) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.beginDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.visibility = visibility;
    }
}

export class TournamentsResponse extends PagedResponse {
    constructor(p) {
        super(p);
        this.pageItems = this.pageItems.map(i =>
            new Tournament(
                i.id,
                i.name,
                i.language,
                i.startDate,
                i.endDate,
                i.state,
                i.visibility
            ));
    }
}