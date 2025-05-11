package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.DTO.LoginRequest;
import com.rooms.RoomKhojo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
