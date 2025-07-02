package com.rooms.RoomKhojo.Service;

import com.rooms.RoomKhojo.DTO.LoginRequest;
import com.rooms.RoomKhojo.Entity.Owner;
import com.rooms.RoomKhojo.Exception.InvalidCredentialsException;
import com.rooms.RoomKhojo.Security.JwtUtil;
import com.rooms.RoomKhojo.repository.CustomerRepository;
import com.rooms.RoomKhojo.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        // Admin check
        if (email.equals("admin@roomkhojo.com")) {
            if (password.equals("admin123")) {
                return jwtUtil.generateToken(email, "ADMIN", 1L); // Admin ID = 0 (or any fixed value)
            } else {
                throw new InvalidCredentialsException("password:Incorrect password");
            }
        }

// Customer check
        var customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isPresent()) {
            var customer = customerOpt.get();
            if (customer.getPassword().equals(password)) {
                return jwtUtil.generateToken(email, "CUSTOMER", customer.getId());
            } else {
                throw new InvalidCredentialsException("password:Incorrect password");
            }
        }

// Owner check
        var ownerOpt = ownerRepository.findByEmail(email);
        if (ownerOpt.isPresent()) {
            var owner = ownerOpt.get();
            if (owner.getPassword().equals(password)) {
                return jwtUtil.generateToken(email, "OWNER", owner.getId());
            } else {
                throw new InvalidCredentialsException("password:Incorrect password");
            }
        }

        // If no user found at all
        throw new InvalidCredentialsException("email:Email not registered");
    }
}
