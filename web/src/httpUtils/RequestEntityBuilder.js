import { QueryParams } from "./QueryParams";
import { HttpMethod, RequestOptions } from "./RequestOptions";
import { RequestEntity } from "./RequestEntity";

export class RequestEntityBuilder {
    pathParams: [{ name: string, value: string }];
    url: string;
    requestOptions: HttpMethod;
    queryParams: QueryParams;

    static from(url: string) {
        let rb = new RequestEntityBuilder()
        return rb.setURL(url);
    }

    static get(url) {
        let rb = this.from(url);
        return rb.setRequestOptions(RequestOptions.get())
            .setAuthorizationHeader();
    }

    static post(url, body) {
        let rb = this.from(url);
        return rb.setRequestOptions(RequestOptions.post(body))
            .setAuthorizationHeader();
    }

    static put(url, body) {
        let rb = this.from(url);
        return rb.setRequestOptions(RequestOptions.put(body))
            .setAuthorizationHeader();
    }

    setRequestOptions(requestOptions: HttpMethod) {
        this.requestOptions = requestOptions;
        return this;
    }

    setURL(url: String) {
        this.url = url;
        return this;
    }

    setQueryParams(queryParams: QueryParams) {
        this.queryParams = queryParams;
        return this;
    }

    setPathParms(...pathParms) {
        this.pathParams = pathParms;
        return this;
    }

    setHeader({ name: name, value: value }) {
        Object.assign(this.requestOptions.headers, { [name]: value });
        return this;
    }

    setAuthorizationHeader() {
        const cookieName = 'wordle-session';
        const cookie = document.cookie
            .split('; ')
            .reverse()
            .find(c => c.indexOf(cookieName) === 0);
        if (cookie) {
            const token = cookie.split('=')[1];
            this.setHeader({ name: 'Authorization', value: 'Bearer ' + token })
        }
        return this;
    }

    build() {
        return new RequestEntity({
            url: this.url,
            requestOptions: this.requestOptions,
            pathParams: this.pathParams,
            queryParams: this.queryParams
        });
    }
}