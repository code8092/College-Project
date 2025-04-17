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
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") long id) throws Exception {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        Customer saveCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(saveCustomer,HttpStatus.CREATED);
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
