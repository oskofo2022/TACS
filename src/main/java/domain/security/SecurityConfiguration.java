package domain.security;

import constants.ApplicationProperties;
import constants.UriConstants;
import domain.security.filters.AuthenticationAdapterRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value(ApplicationProperties.Wordle.Cors.Origin.Arguments.PATTERN)
    private String corsOriginPattern;

    private final AuthenticationAdapterRequestFilter  authenticationAdapterRequestFilter;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(AuthenticationAdapterRequestFilter authenticationAdapterRequestFilter, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.authenticationAdapterRequestFilter = authenticationAdapterRequestFilter;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeRequests().anyRequest().permitAll();
        http.cors();
        */
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeRequests(eiur -> {
                eiur.antMatchers(UriConstants.AntMatchers.getPermitAllWhitelist()).permitAll()
                    .anyRequest().authenticated();
            })
            .addFilterBefore(this.authenticationAdapterRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(this.corsOriginPattern, new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
