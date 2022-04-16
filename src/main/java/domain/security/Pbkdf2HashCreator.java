package domain.security;

import constants.ApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Pbkdf2HashCreator {

    @Value(ApplicationProperties.Wordle.Hash.Arguments.SECRET)
    private String secret;

    @Value(ApplicationProperties.Wordle.Hash.Arguments.ITERATIONS)
    private int iterations;

    @Value(ApplicationProperties.Wordle.Hash.Arguments.WIDTH)
    private int width;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(this.secret, this.iterations, this.width);
    }
}
