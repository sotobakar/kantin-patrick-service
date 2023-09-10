package com.sotobakar.kantinpatrickservice.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String name) {
        super(name + " is not found.");
    }
}
