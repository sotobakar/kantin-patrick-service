package com.sotobakar.kantinpatrickservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateMenuRequest {

    @Size(max = 30)
    private String name;

    private MultipartFile photo;

    private String photoName;

    @Min(0)
    private int price;
}
