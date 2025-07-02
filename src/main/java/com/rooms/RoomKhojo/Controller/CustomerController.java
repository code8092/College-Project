package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.Entity.Customer;
import com.rooms.RoomKhojo.Security.JwtUtil;
import com.rooms.RoomKhojo.Service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@Tag(name = "Customer API", description = "Operation related to Customer API.")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all customers", description = "Returns all customers")
    public ResponseEntity<Map<String, Object>> getAllCustomer() {
        List<Customer> customers = customerService.getAllCustomer();
        return buildResponse("Customers fetched successfully", customers, null, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @Operation(summary = "Get one customer", description = "Returns one customer by ID")
    public ResponseEntity<Map<String, Object>> getCustomerById(@PathVariable("id") long id) throws Exception {
        Customer customer = customerService.getCustomerById(id);
        return buildResponse("Customer fetched successfully", customer, null, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    @Operation(summary = "Create a customer", description = "Create a customer to the database.")
    public ResponseEntity<Map<String, Object>> saveCustomer(@Valid @RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);
            return buildResponse("Customer created successfully", savedCustomer, null, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("email", ex.getMessage());
            return buildResponse("Customer creation failed", null, errors, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping
    public ResponseEntity<Map<String, Object>> updateCustomer(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Customer customerDetails) {

        String token = authHeader.substring(7);
        long customerId = jwtUtil.getCustomerIdFromToken(token); // You'll need to implement this

        Customer updatedCustomer = customerService.updateCustomer(customerId, customerDetails);
        return buildResponse("Customer updated successfully", updatedCustomer, null, HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @CrossOrigin
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer", description = "Deletes a customer using their ID")
    public ResponseEntity<Map<String, Object>> deleteCustomer(@PathVariable("id") long id) {
        boolean isDeleted = customerService.deleteCustomer(id);
        if (isDeleted) {
            return buildResponse("Customer deleted successfully", new Object[]{}, null, HttpStatus.NO_CONTENT);
        } else {
            return buildResponse("Customer not found", new Object[]{}, Map.of("id", "Customer not found"), HttpStatus.NOT_FOUND);
        }
    }

    // Helper method to build a structured response
    private ResponseEntity<Map<String, Object>> buildResponse(String message, Object body, Map<String, String> errors, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("body", body);
        response.put("errors", errors);
        response.put("status", status.value());
        return new ResponseEntity<>(response, status);
    }
}
