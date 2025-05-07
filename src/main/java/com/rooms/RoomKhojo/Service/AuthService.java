package com.rooms.RoomKhojo.Service;

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

    public String loginCustomer(String username, String password) {
        return customerRepository.findByUsername(username)
                .filter(customer -> customer.getPassword().equals(password)) // Replace with password encoder check in production
                .map(customer -> "Login successful for Customer: " + customer.getName())
                .orElse("Invalid username or password for Customer");
    }

    public String loginOwner(String username, String password) {
        return ownerRepository.findByUsername(username)
                .filter(owner -> owner.getPassword().equals(password)) // Replace with password encoder check in production
                .map(owner -> "Login successful for Owner: " + owner.getName())
                .orElse("Invalid username or password for Owner");
    }
}
