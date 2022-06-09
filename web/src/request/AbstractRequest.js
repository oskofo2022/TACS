import {UnauthorizedException} from "errors/UnauthorizedException";


export class AbstractRequest {
    url;
    pathParams;
    request;
    body;
    response;

    constructor({
        pathParams=[],
        url='', 
        request={fetch: () => {}},
        body={}
    }){
        this.url = url;
        this.pathParams = pathParams
        this.request = request
        this.body = body
        this.response = {}
    }

    buildRequest = () => {
        throw new Error("Method not implemented.");
    }

    async fetch(){
        this.response = await this.request.fetch();
        if(this.response.status === 401) throw new UnauthorizedException('Unauthorized');
        return this.response;
    }

    async fetchAsJSON(){
        await this.fetch()
        return this.response.json();
    }
}