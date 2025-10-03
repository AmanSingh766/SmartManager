package com.scm.helpers;

import com.scm.entities.User;
import com.scm.repsitories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    // Base URL of your frontend or backend verification endpoint
    private static final String BASE_VERIFICATION_URL = "http://localhost:8080/api/auth/verify-email?token=";

    @Autowired
    private UserRepo userRepo;

    /**
     * Generates a full email verification link using the email token
     *
     * @param emailToken The unique token for email verification
     * @return Full URL to be sent in email
     */
    public String getLinkForEmailVerification(String emailToken) {
        if (emailToken == null || emailToken.isBlank()) {
            throw new IllegalArgumentException("Email token cannot be null or empty");
        }
        return BASE_VERIFICATION_URL + emailToken;
    }

    /**
     * Alternative method with corrected spelling (for backward compatibility)
     *
     * @param emailToken The unique token for email verification
     * @return Full URL to be sent in email
     */
    public String getLinkForEmailVerificationCorrected(String emailToken) {
        return getLinkForEmailVerification(emailToken); // simply calls the main method
    }

    /**
     * Returns the email of the currently logged-in user
     *
     * @param authentication Authentication object from Spring Security
     * @return Email of logged-in user or null if not authenticated
     */
    public static String getEmailOfLoggedInUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        // Standard username/password login
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername(); // usually email
        }

        // OAuth2 login (Google/GitHub)
        if (principal instanceof DefaultOAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");
            if (email != null && !email.isBlank()) {
                return email;
            }
            // Fallback for GitHub username
            String login = oauth2User.getAttribute("login");
            if (login != null && !login.isBlank()) {
                return login + "@github.com"; // fallback format
            }
        }

        // Fallback for unknown principal
        return principal.toString();
    }

    /**
     * Returns the User entity of the currently logged-in user from the database
     *
     * @param authentication Authentication object from Spring Security
     * @return User entity or null if not authenticated
     */
    public User getLoggedInUser(Authentication authentication) {
        String email = getEmailOfLoggedInUser(authentication);
        if (email == null) return null;
        return userRepo.findByEmail(email).orElse(null);
    }
}
