package com.rooms.RoomKhojo.Service;
import com.rooms.RoomKhojo.Entity.Customer;
import com.rooms.RoomKhojo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Customer with email already exists");
        }
        return customerRepository.save(customer);
    }


    public Customer getCustomerById(long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found for "+id));
    }

    public Customer updateCustomer(long id, Customer customerDetails) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found for "+id));

        existingCustomer.setName(customerDetails.getName());
        existingCustomer.setPhoneNo(customerDetails.getPhoneNo());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setGeneder(customerDetails.getGeneder());
        existingCustomer.setPassword(customerDetails.getPassword());

        return customerRepository.save(existingCustomer);
    }

    public boolean deleteCustomer(long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        throw new RuntimeException("Customer with "+id+"is not deleted");
    }
}
