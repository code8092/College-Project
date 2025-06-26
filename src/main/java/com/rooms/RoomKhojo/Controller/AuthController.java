package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.DTO.LoginRequest;
import com.rooms.RoomKhojo.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);

        Map<String, String> tokenBody = new HashMap<>();
        tokenBody.put("token", token);

        return buildResponse("Login successful", tokenBody, null, HttpStatus.OK);
    }

    // Helper method for consistent structured response
    private ResponseEntity<Map<String, Object>> buildResponse(String message, Object body, Map<String, String> errors, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("body", body);
        response.put("errors", errors);
        response.put("status", status.value());
        return new ResponseEntity<>(response, status);
    }

    // Nested secure controller for demonstration (also structured)
    @RestController
    @RequestMapping("/secure")
    public static class SecureController {
        @GetMapping
        public ResponseEntity<Map<String, Object>> secureEndpoint() {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Access granted");
            response.put("body", "You have accessed a secured endpoint!");
            response.put("errors", null);
            response.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
