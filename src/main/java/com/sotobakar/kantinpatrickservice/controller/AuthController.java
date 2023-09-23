package com.sotobakar.kantinpatrickservice.controller;

import com.sotobakar.kantinpatrickservice.dto.response.UserResponse;
import com.sotobakar.kantinpatrickservice.service.UserService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public UserResponse getCurrentlyAuthenticatedUser(@AuthenticationPrincipal Jwt jwt) {
        return this.modelMapper.map(this.userService.findOrCreateUserByEmail(jwt.getClaimAsString("email")), UserResponse.class);
    }
}
