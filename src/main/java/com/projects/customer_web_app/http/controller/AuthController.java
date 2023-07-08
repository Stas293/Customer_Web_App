package com.projects.customer_web_app.http.controller;

import com.projects.customer_web_app.dto.UserRegistrationDto;
import com.projects.customer_web_app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") UserRegistrationDto user) {
        return "registration";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request,
                           @ModelAttribute("user") @Valid UserRegistrationDto user,
                           BindingResult result,
                           Model model) {
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        user.setCaptchaResponse(gRecaptchaResponse);
        userService.save(user, result);
        if (result.hasErrors()) {
            log.error("Errors: {}", result.getAllErrors());
            model.addAttribute("user", user);
            model.addAttribute("errors", result.getAllErrors());
            return "registration";
        }
        return "redirect:/login";
    }
}
