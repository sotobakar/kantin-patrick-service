package com.sotobakar.kantinpatrickservice.dto.response;

import lombok.Data;

@Data
public class MenuResponse {
    private Long id;
    private String name;
    private String photo;
    private int price;
}
