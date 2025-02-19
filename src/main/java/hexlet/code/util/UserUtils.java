package hexlet.code.util;

import hexlet.code.repository.UserRepository;
import hexlet.code.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility class for user-related operations and authentication checks.
 * Provides methods for accessing the current authenticated user and checking authentication status.
 */
@Component
public class UserUtils {
    /**
     * Repository for accessing user data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves the currently authenticated user from the security context.
     * Returns null if no user is authenticated.
     * Throws exception if user is not found in the database.
     *
     * @return the current authenticated user
     */
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    /**
     * Checks if there is an authenticated user in the current security context.
     *
     * @return true if user is authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
