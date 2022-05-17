import {PagedResponse} from "./PagedResponse";

class Position {
    constructor(id, pos, name, points) {
        this.id = id;
        this.position = pos;
        this.name = name;
        this.points = points;
    }
}

export class PositionsResponse extends PagedResponse {
    constructor(p) {
        super(p);
        let positionList = []
        this.pageItems = this.pageItems.map(tournament => {
            let positionIndex = 0;
            tournament.positions.map((position,idx,array) => {
                if (idx === 0 || position.guessesCount !== array[idx-1].guessesCount) {
                    positionIndex += 1;
                }
                positionList.push(new Position(idx, positionIndex, position.name, position.guessesCount));
            })
        });
        this.pageItems = positionList;
    }
}