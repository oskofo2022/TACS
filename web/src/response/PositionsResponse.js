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
        let positionList = []
        debugger;
        this.pageItems = this.pageItems.map(tournament => {
            // const tournamentName = tournament.name;
            let positionIndex = 0;
            // let positionList = [];
            tournament.positions.map(position => {
                positionIndex += 1;
                positionList.push(new Position(positionIndex, position.name, position.guessesCount));
                const newPosition = new Position(positionIndex, position.name, position.guessesCount);
                this.position = newPosition;
            })
            return positionList;
            // return tournament.positions;
        });
        this.pageItems = positionList;
    }
}