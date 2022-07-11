import {PagedResponse} from "./PagedResponse";

export class Tournament {
    constructor(id, date, language, guessesCount) {
        this.id = id;
        this.date = date;
        this.language = language;
        this.guessesCount = guessesCount;
    }
}

export class MyGamesResponse extends PagedResponse {
    constructor(p) {
        super(p);
        this.pageItems = this.pageItems.map((game, i) =>
            new Tournament(
                i,
                game.date,
                game.language,
                game.guessesCount
            ));
    }
}