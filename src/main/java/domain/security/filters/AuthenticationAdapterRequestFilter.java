package domain.security.filters;

import constants.ApplicationProperties;
import constants.HeadersConstants;
import constants.UriConstants;
import domain.security.JwtService;
import domain.security.WordleUserDetailsService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthenticationAdapterRequestFilter extends OncePerRequestFilter {

    @Value(ApplicationProperties.Wordle.Cookie.Arguments.NAME)
    public String cookieName;

    private final WordleUserDetailsService wordleUserDetailsService;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationAdapterRequestFilter(JwtService jwtService, WordleUserDetailsService wordleUserDetailsService) {
        this.jwtService = jwtService;
        this.wordleUserDetailsService = wordleUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (this.needsAuthorization(request)) {

            final var optionalClaims = this.getJwt(request)
                                                          .map(this::getClaims);

            if (optionalClaims.isEmpty()) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            this.authenticate(optionalClaims.get(), request);
        }

        chain.doFilter(request, response);
    }

    private void authenticate(Claims claims, HttpServletRequest request) {
        final var userDetails = this.wordleUserDetailsService.loadUserByUsername(claims.getSubject());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private Optional<String> getJwt(HttpServletRequest request) {
        final var authorizationHeader = request.getHeader(HeadersConstants.AUTHORIZATION);

        return Optional.ofNullable(authorizationHeader)
                       .map(s -> s.substring(HeadersConstants.Values.Bearer.length()))
                       .or(() -> this.getJwtByCookies(request));
    }

    @Deprecated
    //Will be removed after swagger is configured to send an authorization bearer
    private Optional<String> getJwtByCookies(HttpServletRequest request) {
        var cookies = request.getCookies();
        if (cookies == null) {
            cookies = new Cookie[] {};
        }

        return Arrays.stream(cookies)
                     .filter(c -> c.getName().equals(this.cookieName))
                     .findFirst()
                     .map(Cookie::getValue);
    }

    private Claims getClaims(String jwt) {
        try {
            return this.jwtService.extractClaims(jwt);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private boolean needsAuthorization(HttpServletRequest request) {
        final var requestURI = request.getRequestURI();
        // TODO: Unify URI constants using uri whitelist only
        return Arrays.stream(UriConstants.AuthenticationAdapterRequestFilter.getPermitAllWhitelist()).noneMatch(uw -> uw.match(request))
               || UriConstants.AuthenticationAdapterRequestFilter.getExcludeExceptionsWhitelist().anyMatch(p -> p.matcher(requestURI).matches());
    }
}
