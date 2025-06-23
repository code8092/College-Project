package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.DTO.LoginRequest;
import com.rooms.RoomKhojo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Login successful");
        response.put("Token", token);
        return ResponseEntity.ok(response);
    }


    @RestController
    @RequestMapping("/secure")
    public class SecureController {

        @GetMapping
        public String secureEndpoint() {
            return "You have accessed a secured endpoint!";
        }
    }
}
