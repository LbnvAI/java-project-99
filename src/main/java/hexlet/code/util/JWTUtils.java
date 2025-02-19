package hexlet.code.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for JWT token generation operations.
 * Handles creation of signed JWT tokens with configurable expiration.
 */
@Component
public class JWTUtils {

    /**
     * Spring Security JWT encoder for signing tokens.
     */
    @Autowired
    private JwtEncoder encoder;

    /**
     * Generates a JWT token for the given username.
     * The token includes issuer, issued-at time, expiration time, and subject claims.
     * Token expires after 1 hour from creation.
     *
     * @param username the subject to include in the token
     * @return generated JWT token string
     */
    public String generateToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(username)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
