package com.sotobakar.kantinpatrickservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefaultResponse<T> extends BasicResponse {
    public DefaultResponse(String statusCode, String message, T data) {
        this.setStatusCode(statusCode);
        this.setMessage(message);
        this.setData(data);
    }
    private T data;
}
