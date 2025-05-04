package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.Entity.Customer;
import com.rooms.RoomKhojo.Service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@Tag(name = "Customer API", description = "Operation related to Customer API.")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @Operation(summary = "Get all customers", description = "Returns all customers")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customers = customerService.getAllCustomer();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one customer", description = "Returns one customer by ID")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) throws Exception {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    @Operation(summary = "Save a customer", description = "Saves a customer to the database.")
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a customer", description = "Updates customer info like name, phone, email by ID")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable("id") long id,
            @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer); // Let service throw if not found
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a customer", description = "Deletes a customer using their ID")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") long id) {
        boolean isDeleted = customerService.deleteCustomer(id);
        return isDeleted
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
