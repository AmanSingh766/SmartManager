package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Helper helper;

    /**
     * Utility method to get the currently logged-in user
     */
    private User getVerifiedUser(Authentication authentication, Model model) {
        // Get email from Authentication
        String email = helper.getEmailOfLoggedInUser(authentication);

        // Fetch user by email
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        return user;
    }

    /**
     * Example endpoint that uses the logged-in user
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User user = getVerifiedUser(authentication, model);

        // Add user to model to use in Thymeleaf or JSP
        model.addAttribute("user", user);

        return "dashboard"; // view name
    }
}
