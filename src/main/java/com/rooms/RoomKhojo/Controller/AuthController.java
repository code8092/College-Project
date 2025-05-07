package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.DTO.LoginRequest;
import com.rooms.RoomKhojo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/customer/login")
    public String loginCustomer(@RequestBody LoginRequest loginRequest) {
        return authService.loginCustomer(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/owner/login")
    public String loginOwner(@RequestBody LoginRequest loginRequest) {
        return authService.loginOwner(loginRequest.getUsername(), loginRequest.getPassword());
    }
}

