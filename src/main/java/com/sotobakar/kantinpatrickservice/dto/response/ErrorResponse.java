package com.sotobakar.kantinpatrickservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends BasicResponse {
    private String statusCode;
    private String message;
}
