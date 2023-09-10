package com.sotobakar.kantinpatrickservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateItemInCartRequest {
    @NotNull
    private Long menuId;

    @NotNull
    @Min(0)
    @Max(100)
    private int quantity;
}
