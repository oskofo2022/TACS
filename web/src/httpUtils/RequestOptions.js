export class HttpMethod {
    method;
    mode;
    headers;
    constructor(method, mode, headers) {
        this.method = method;
        this.mode = mode;
        this.headers = headers;
    }
}

class Get extends HttpMethod {
    constructor({
                method = 'GET',
                mode = 'cors',
                headers = {'Content-Type': 'application/json'}
            }){
        super(method, mode, headers);
    }
}

class Post extends HttpMethod{
    constructor({
                method = 'POST',
                mode = 'cors',
                headers = {'Content-Type': 'application/json'},
                body = {},
                credentials = 'include'
            }){
        super(method, mode, headers);
        this.body = body;
        this.credentials = credentials;
    }
}

class Put extends HttpMethod{
    constructor({
                    method = 'PUT',
                    mode = 'cors',
                    headers = {'Content-Type': 'application/json'},
                    body = {},
                    credentials = 'include'
                }){
        super(method, mode, headers);
        this.body = body;
        this.credentials = credentials;
    }
}

export class RequestOptions {
    static get = () => (new Get({}));
    static post = (body) => (new Post({body: body}));
    static put = (body) => (new Put({body: body}));
}