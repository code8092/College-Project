package com.rooms.RoomKhojo.Service;

import com.rooms.RoomKhojo.DTO.LoginRequest;
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

    public String login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        // for customer login
        var customerResult = customerRepository.findByEmail(email)
                .filter(customer -> customer.getPassword().equals(password));

        if (customerResult.isPresent()) {
            return "Login successful for Customer: " + customerResult.get().getName();
        }

        // for owner login
        var ownerResult = ownerRepository.findByEmail(email)
                .filter(owner -> owner.getPassword().equals(password));

        if (ownerResult.isPresent()) {
            return "Login successful for Owner: " + ownerResult.get().getName();
        }

        return "Invalid email or password";
    }
}
