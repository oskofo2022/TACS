package domain.security;

import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

public class UriWhitelist {
    private final String uri;
    private final List<RequestMethod> methods;
    private final UriComparer uriComparer;

    public UriWhitelist(String uri, List<RequestMethod> methods, UriComparer uriComparer) {
        this.uri = uri;
        this.methods = methods;
        this.uriComparer = uriComparer;
    }

    public UriWhitelist(String uri) {
        this.uri = uri;
        this.methods = Collections.emptyList();
        this.uriComparer = UriComparer.CONTAINS;
    }

    public boolean match(HttpServletRequest request) {
        final var requestUri = request.getRequestURI();
        final var method = request.getMethod();
        return this.uriComparer.match(this.uri, requestUri) && this.methods.stream().allMatch(rm -> rm == RequestMethod.valueOf(method));
    }
}
