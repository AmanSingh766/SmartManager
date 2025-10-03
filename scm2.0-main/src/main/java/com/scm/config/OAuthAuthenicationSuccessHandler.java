package com.scm.config;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repsitories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        String providerId = oauthToken.getAuthorizedClientRegistrationId();

        // Determine email
        String email;
        if ("google".equalsIgnoreCase(providerId)) {
            email = oauthUser.getAttribute("email");
        } else {
            email = oauthUser.getAttribute("email") != null
                    ? oauthUser.getAttribute("email")
                    : oauthUser.getAttribute("login") + "@gmail.com";
        }

        // Check if user exists
        User user = userRepo.findByEmail(email).orElse(null);

        if (user == null) {
            user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setEmail(email);
            user.setName("google".equalsIgnoreCase(providerId) ? oauthUser.getAttribute("name") : oauthUser.getAttribute("login"));
            user.setProfilePic("google".equalsIgnoreCase(providerId) ? oauthUser.getAttribute("picture") : oauthUser.getAttribute("avatar_url"));
            user.setProvider("google".equalsIgnoreCase(providerId) ? Providers.GOOGLE : Providers.GITHUB);
            user.setProviderUserId(oauthUser.getName());
            user.setEnabled(true);
            user.setEmailVerified(true);
            user.setRoleList(List.of(AppConstants.ROLE_USER));

            userRepo.save(user);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
