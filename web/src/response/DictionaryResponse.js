

class Meaning {
    meanings;
    constructor(type, definition) {
        this.type = type;
        this.definition = definition;
    }
}

export class DictionaryResponse{
    constructor(meanings) {
        this.meanings = meanings.map(m =>
            new Meaning(
                m.type,
                m.definition
            ));
    }
}