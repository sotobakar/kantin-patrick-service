package com.sotobakar.kantinpatrickservice.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponse {
    private Long id;

    private UserResponse user;

    private List<CartItemResponse> cartItems = new ArrayList<>();
}
