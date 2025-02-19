package hexlet.code.controller.api;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hexlet.code.dto.AuthRequest;
import hexlet.code.util.JWTUtils;

/**
 * REST controller for handling authentication-related endpoints.
 * Provides login functionality and JWT token generation.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthenticationController {

    /**
     * Utility class for JWT token operations.
     */
    private final JWTUtils jwtUtils;

    /**
     * Spring Security authentication manager for processing authentication requests.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Handles login requests and returns a JWT token upon successful authentication.
     *
     * @param authRequest contains username and password for authentication
     * @return generated JWT token if authentication is successful
     */
    @PostMapping("/login")
    public String create(@RequestBody AuthRequest authRequest) {
        var authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword());
        authenticationManager.authenticate(authentication);
        return jwtUtils.generateToken(authRequest.getUsername());
    }
}
