package com.rooms.RoomKhojo.Service;

import com.rooms.RoomKhojo.DTO.LoginRequest;
import com.rooms.RoomKhojo.Entity.Owner;
import com.rooms.RoomKhojo.Entity.Customer;
import com.rooms.RoomKhojo.Exception.InvalidCredentialsException;
import com.rooms.RoomKhojo.Security.JwtUtil;
import com.rooms.RoomKhojo.repository.CustomerRepository;
import com.rooms.RoomKhojo.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        Map<String, Object> response = new HashMap<>();

        // Admin login
        if (email.equals("admin@roomkhojo.com")) {
            if (password.equals("admin123")) {
                String token = jwtUtil.generateToken(email, "ADMIN", 1L);
                response.put("token", token);
                response.put("id", 1L);
                response.put("role", "ADMIN");
                return response;
            } else {
                throw new InvalidCredentialsException("password:Incorrect password");
            }
        }

        // Customer login
        var customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.getPassword().equals(password)) {
                String token = jwtUtil.generateToken(email, "CUSTOMER", customer.getId());
                response.put("token", token);
                response.put("id", customer.getId());
                response.put("role", "CUSTOMER");
                return response;
            } else {
                throw new InvalidCredentialsException("password:Incorrect password");
            }
        }

        // Owner login
        var ownerOpt = ownerRepository.findByEmail(email);
        if (ownerOpt.isPresent()) {
            Owner owner = ownerOpt.get();
            if (owner.getPassword().equals(password)) {
                String token = jwtUtil.generateToken(email, "OWNER", owner.getId());
                response.put("token", token);
                response.put("id", owner.getId());
                response.put("role", "OWNER");
                return response;
            } else {
                throw new InvalidCredentialsException("password:Incorrect password");
            }
        }

        throw new InvalidCredentialsException("email:Email not registered");
    }
}
