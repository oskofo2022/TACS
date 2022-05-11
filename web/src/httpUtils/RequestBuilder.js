import {QueryParams} from "./QueryParams";
import {HttpMethod, RequestOptions} from "./RequestOptions";
import {Request} from "./Request";

export class RequestBuilder {
    pathParams: [{ name: string, value: string }];
    url: string;
    requestOptions: HttpMethod;
    queryParams: QueryParams;

    static from(url: string) {
        let rb = new RequestBuilder()
        return rb.setURL(url);
    }

    static get(url) {
        let rb = this.from(url);
        return rb.setRequestOptions(RequestOptions.get());
    }

    static post(url, body) {
        let rb = this.from(url);
        return rb.setRequestOptions(RequestOptions.post(body));
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

    build() {
        return new Request({
            url: this.url,
            requestOptions: this.requestOptions,
            pathParams: this.pathParams,
            queryParams: this.queryParams
        });
    }
}