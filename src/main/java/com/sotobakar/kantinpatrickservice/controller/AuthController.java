package com.sotobakar.kantinpatrickservice.controller;

import com.sotobakar.kantinpatrickservice.model.User;
import com.sotobakar.kantinpatrickservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getCurrentlyAuthenticatedUser(@AuthenticationPrincipal Jwt jwt) {
        return this.userService.findOrCreateUserByEmail(jwt.getClaimAsString("email"));
    }
}
