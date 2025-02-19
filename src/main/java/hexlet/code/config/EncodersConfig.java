package hexlet.code.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import hexlet.code.component.RsaKeyProperties;

/**
 * Configuration class for managing various encoding and decoding mechanisms in the application.
 * Handles password encoding and JWT encoding using RSA keys.
 */
@Configuration
@AllArgsConstructor
public class EncodersConfig {

    /**
     * Object containing public and private RSA keys for JWT token operations.
     */
    private final RsaKeyProperties rsaKeys;

    /**
     * Creates and returns a PasswordEncoder for secure password hashing.
     * Uses the BCrypt algorithm, which is recommended for password storage
     * due to its adaptive complexity and resistance to brute force attacks.
     *
     * @return BCryptPasswordEncoder for password hashing
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates and returns a JWT encoder for signing tokens with RSA keys.
     * Generates JWT tokens signed with the private RSA key, allowing
     * their authenticity to be verified using the public key.
     *
     * @return NimbusJwtEncoder for creating signed JWT tokens
     */
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.getPublicKey())
                .privateKey(rsaKeys.getPrivateKey())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Creates and returns a JWT decoder for verifying token signatures.
     * Uses the public RSA key to verify JWT token signatures.
     *
     * @return NimbusJwtDecoder for verifying JWT token signatures
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.getPublicKey()).build();
    }
}
