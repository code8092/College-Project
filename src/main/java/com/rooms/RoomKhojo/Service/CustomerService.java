package com.rooms.RoomKhojo.Service;

import com.rooms.RoomKhojo.Entity.Customer;
import com.rooms.RoomKhojo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();

    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(long id) throws Exception {
        return customerRepository.findById(id).
                orElseThrow(() -> new Exception("Customer not found with id: " + id));
    }

    public Customer updateCustomer(long id, Customer customerDetails) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isEmpty()) {
            return null;
        }

        // Get the existing customer
        Customer existingCustomer = customerOptional.get();

        // Update the existing customer fields
        existingCustomer.setName(customerDetails.getName());
        existingCustomer.setPhoneNo(customerDetails.getPhoneNo());

        // Save the updated customer
        return customerRepository.save(existingCustomer);
    }

    public boolean deleteCustomer(long id) {

        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
