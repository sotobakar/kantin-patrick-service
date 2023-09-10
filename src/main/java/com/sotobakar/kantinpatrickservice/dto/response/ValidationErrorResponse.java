package com.sotobakar.kantinpatrickservice.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse extends ErrorResponse {
    private List<Violation> errors = new ArrayList<>();
}
