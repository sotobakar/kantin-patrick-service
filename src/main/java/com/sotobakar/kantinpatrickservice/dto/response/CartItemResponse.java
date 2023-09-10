package com.sotobakar.kantinpatrickservice.dto.response;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long id;
    private MenuResponse menu;
    private int quantity;
}
