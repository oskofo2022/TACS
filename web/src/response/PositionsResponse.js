import {PagedResponse} from "./PagedResponse";

class Position {
    constructor(pos, name, points) {
        this.id = pos;
        this.position = pos;
        this.name = name;
        this.points = points;
    }
}

export class PositionsResponse extends PagedResponse {
    constructor(p) {
        super(p);
        this.pageItems = this.pageItems.map(position =>
            new Position(position.cardinal, position.name, position.guessesCount)
        );
    }
}