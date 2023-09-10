package com.sotobakar.kantinpatrickservice.advice;

import com.sotobakar.kantinpatrickservice.dto.response.ErrorResponse;
import com.sotobakar.kantinpatrickservice.dto.response.ValidationErrorResponse;
import com.sotobakar.kantinpatrickservice.dto.response.Violation;
import com.sotobakar.kantinpatrickservice.exception.MinioUploadErrorException;
import com.sotobakar.kantinpatrickservice.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.setStatusCode(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()));
        error.setMessage("There are validation errors.");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.getErrors().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        error.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler({MinioUploadErrorException.class})
    public Object handleMinioUploadError(MinioUploadErrorException ex) {
        ErrorResponse error = new ErrorResponse();

        error.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        error.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
