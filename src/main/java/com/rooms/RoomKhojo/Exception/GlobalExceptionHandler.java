package com.rooms.RoomKhojo.Exception;

import com.rooms.RoomKhojo.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object[]>> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String, String> errors = new HashMap<>();
        String message = ex.getMessage();

        if (message.contains(":")) {
            String[] parts = message.split(":", 2);
            errors.put(parts[0].trim(), parts[1].trim());
            message = "Validation failed";
        }

        ApiResponse<Object[]> response = new ApiResponse<>(
                message,
                new Object[]{}, // empty body
                errors,
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object[]>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<Object[]> response = new ApiResponse<>(
                "Validation failed",
                new Object[]{},
                errors,
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object[]>> handleGenericException(Exception ex) {
        ApiResponse<Object[]> response = new ApiResponse<>(
                "Internal Server Error",
                new Object[]{},
                Collections.emptyMap(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
