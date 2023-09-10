package com.sotobakar.kantinpatrickservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateMenuRequest {

    @NotNull
    @Size(min = 0, max = 30)
    private String name;

    @NotNull
    private MultipartFile photo;

    private String photoName;

    @Min(0)
    private int price;
}
