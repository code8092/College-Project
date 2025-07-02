package com.rooms.RoomKhojo.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public class ResponseUtil {
    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T body) {
        return ResponseEntity.ok(new ApiResponse<>(message, body, null, 200));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(message, body, null, 201));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message, Map<String, String> errors, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse<>(message, null, errors, status.value()));
    }
}
