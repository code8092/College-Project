package com.rooms.RoomKhojo.Service;

import com.rooms.RoomKhojo.DTO.LoginRequest;
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

        var customerResult = customerRepository.findByEmail(email)
                .filter(customer -> customer.getPassword().equals(password));

        if (customerResult.isPresent()) {
            return jwtUtil.generateToken(email, "CUSTOMER");
        }

        var ownerResult = ownerRepository.findByEmail(email)
                .filter(owner -> owner.getPassword().equals(password));

        if (ownerResult.isPresent()) {
            return jwtUtil.generateToken(email, "OWNER");
        }

        // If no match, throw exception
        throw new InvalidCredentialsException("Invalid email or password");
    }
}
