package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.DTO.LoginRequest;
import com.rooms.RoomKhojo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        return ResponseEntity.ok("Login successful. Token: " + token);
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
