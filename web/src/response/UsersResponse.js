import {PagedResponse} from "./PagedResponse";

class User {
    constructor(id, name, email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

export class UsersResponse extends PagedResponse {
    constructor(p) {
        super(p);
        this.pageItems = this.pageItems.map(user =>
            new User(user.id, user.name, user.email)
        );
    }
}