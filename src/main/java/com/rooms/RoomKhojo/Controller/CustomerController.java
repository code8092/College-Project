package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.Entity.Customer;
import com.rooms.RoomKhojo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getallCustomer(){
        List<Customer> customers = customerService.getAllCustomer();
        try {
            return new ResponseEntity<>(customers,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") long id) throws Exception {
        try {
            return customerService.getCustomerById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        Customer saveCustomer = customerService.saveCustomer(customer);
        try {
            return new ResponseEntity<>(saveCustomer,HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id,@RequestBody Customer customerDetails){
        Customer updatedCustomer = customerService.updateCustomer(id,customerDetails);
        if(updatedCustomer!=null){
            return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") long id){
        boolean isDeleted = customerService.deleteCustomer(id);
        if(isDeleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
