class Get {
    constructor({
                method = 'GET',
                mode = 'cors',
                headers = {'Content-Type': 'application/json'}
            }){
        this.method = method;
        this.mode = mode;
        this.headers = headers;
    }
}

class Post {
    constructor({
                method = 'POST',
                mode = 'cors',
                headers = {'Content-Type': 'application/json'},
                body = {},
                credentials = 'include'
            }){
        this.method = method;
        this.mode = mode;
        this.headers = headers;
        this.body = body;
        this.credentials = credentials;
    }
}

export class Request {
    static get = () => (new Get({}))
    static post = (body) => (new Post({body: body}))
}