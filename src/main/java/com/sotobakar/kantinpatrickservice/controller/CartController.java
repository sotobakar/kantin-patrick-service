package com.sotobakar.kantinpatrickservice.controller;

import com.sotobakar.kantinpatrickservice.dto.request.UpdateItemInCartRequest;
import com.sotobakar.kantinpatrickservice.dto.response.CartResponse;
import com.sotobakar.kantinpatrickservice.dto.response.DefaultResponse;
import com.sotobakar.kantinpatrickservice.model.Cart;
import com.sotobakar.kantinpatrickservice.model.User;
import com.sotobakar.kantinpatrickservice.service.CartService;
import com.sotobakar.kantinpatrickservice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    private final ModelMapper modelMapper;
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(ModelMapper modelMapper, CartService cartService, UserService userService) {
        this.modelMapper = modelMapper;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<CartResponse>> getCart(@AuthenticationPrincipal Jwt jwt) {
        // Get User
        User user = this.userService.findOrCreateUserByEmail(jwt.getClaimAsString("email"));

        // Get List
        CartResponse cart = this.modelMapper.map(this.cartService.get(user), CartResponse.class);

        DefaultResponse<CartResponse> response = new DefaultResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                "Shopping cart",
                cart
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @PutMapping
    public ResponseEntity<DefaultResponse<CartResponse>> updateItemInCart(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody() UpdateItemInCartRequest cartData) {
        // Get User
        User user = this.userService.findOrCreateUserByEmail(jwt.getClaimAsString("email"));

        // Get Cart
        Cart cart = this.cartService.get(user);

        // Add items to cart
        CartResponse updatedCart = this.modelMapper.map(this.cartService.updateItem(cartData, cart), CartResponse.class);

        DefaultResponse<CartResponse> response = new DefaultResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                "Shopping cart",
                updatedCart
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }
}
