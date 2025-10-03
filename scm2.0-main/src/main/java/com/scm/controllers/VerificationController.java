package com.scm.controllers;

import com.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerificationController {

    @Autowired
    private UserService userService;

    @GetMapping("/auth/verify")
    public String verifyEmail(@RequestParam("token") String token, Model model) {
        String message = userService.verifyEmail(token);
        model.addAttribute("message", message);
        return "auth/verify_result"; // create Thymeleaf page to show result
    }
}
