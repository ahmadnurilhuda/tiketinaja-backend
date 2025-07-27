package com.greenacademy.tiketinaja.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.greenacademy.tiketinaja.common.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceAlreadyExistsException(ResourceNotFoundException ex) {
        return ResponseEntity.status(409).body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        List<ErrorMessage> errorMessages = new ArrayList<>();
        for (FieldError error : errors) {
            ErrorMessage errorMessage = new ErrorMessage(error.getDefaultMessage(), error.getField());
            errorMessages.add(errorMessage);
        }
        return ResponseEntity.status(400).body(new ApiResponse<>(false, "Validation Error / Bad Request / Failed", errorMessages));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
            false,
            "Image size must be less than 5MB",
            null
        );
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
    }

    // @ExceptionHandler(UnauthorizedException.class)
    // public ResponseEntity<ApiResponse<Object>> handleUnauthorizedException(UnauthorizedException ex) {
    //     return ResponseEntity.status(401).body(new ApiResponse<>(false, ex.getMessage(), null));
    // }

    // @ExceptionHandler(UnauthenticatedException.class)
    // public ResponseEntity<ApiResponse<Object>> handleUnauthenticatedException(UnauthenticatedException ex) {
    //     return ResponseEntity.status(401).body(new ApiResponse<>(false, ex.getMessage(), null));
    // }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<Object>> handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity.status(403).body(new ApiResponse<>(false, ex.getMessage(), null));
    }
}