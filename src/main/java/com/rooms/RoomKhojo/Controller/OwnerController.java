package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.DTO.ResidentialPropertyDTO;
import com.rooms.RoomKhojo.Entity.Owner;
import com.rooms.RoomKhojo.Entity.ResidentialProperty;
import com.rooms.RoomKhojo.Security.JwtUtil;
import com.rooms.RoomKhojo.Service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/Owner")
@Tag(name = "Owner API", description = "Operation related to Owner API")
@CrossOrigin
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    // Helper method for consistent JSON response
    private ResponseEntity<Map<String, Object>> buildResponse(String message, Object body, Map<String, String> errors, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("body", body);
        response.put("errors", errors);
        response.put("status", status.value());
        return new ResponseEntity<>(response, status);
    }

    @CrossOrigin
    @PostMapping
    @Operation(summary = "Create an owner", description = "This method creates an owner")
    public ResponseEntity<Map<String, Object>> saveOwner(@Valid @RequestBody Owner owner) {
        try {
            Owner savedOwner = ownerService.saveOwner(owner);
            return buildResponse("Owner created successfully", savedOwner, null, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("email", ex.getMessage());
            return buildResponse("Owner creation failed", null, errors, HttpStatus.BAD_REQUEST);
        }
    }


    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/property")
    @PreAuthorize("hasRole('OWNER')")
    @Operation(summary = "Add property for logged-in owner", description = "This adds a property using JWT owner ID")
    public ResponseEntity<Map<String, Object>> addPropertyToOwner(
            @Valid @RequestBody ResidentialPropertyDTO residentialPropertyDTO,
            @RequestHeader("Authorization") String authHeader) {

        // Extract token from header and remove "Bearer " prefix
        String token = authHeader.substring(7);
        Long ownerId = jwtUtil.getOwnerIdFromToken(token);

        ResidentialProperty savedProperty = ownerService.addPropertyToOwner(ownerId, residentialPropertyDTO);
        return buildResponse("Property added successfully", savedProperty, null, HttpStatus.OK);
    }


    @GetMapping("/properties")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Map<String, Object>> getPropertiesByOwner(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        Long ownerId = jwtUtil.getOwnerIdFromToken(token);

        List<ResidentialProperty> properties = ownerService.getPropertiesByOwnerId(ownerId);
        return buildResponse("Properties retrieved successfully", properties, null, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all owners", description = "Returns all owners")
    public ResponseEntity<Map<String, Object>> getAllOwner() {
        List<Owner> owners = ownerService.getAllOwner();
        if (owners.isEmpty()) {
            return buildResponse("No owners found", Collections.emptyList(), null, HttpStatus.NO_CONTENT);
        }
        return buildResponse("Owners retrieved successfully", owners, null, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get one owner by ID", description = "Returns one owner")
    public ResponseEntity<Map<String, Object>> getOne(@PathVariable("id") Long id) {
        Optional<Owner> owner = ownerService.getOne(id);
        return owner
                .map(value -> buildResponse("Owner retrieved successfully", value, null, HttpStatus.OK))
                .orElseGet(() -> buildResponse("Owner not found", null, null, HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateOwner(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Owner ownerDetails) {

        String token = authHeader.substring(7);
        Long ownerId = jwtUtil.getOwnerIdFromToken(token);

        Owner updatedOwner = ownerService.updateOwner(ownerId, ownerDetails);
        return buildResponse("Owner updated successfully", updatedOwner, null, HttpStatus.OK);
    }


    @DeleteMapping("/properties/{propertyId}")
    public ResponseEntity<Map<String, Object>> deletePropertyForOwner(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long propertyId) {

        String token = authHeader.substring(7);
        Long ownerId = jwtUtil.getOwnerIdFromToken(token);

        ownerService.deletePropertyForOwner(ownerId, propertyId);
        return buildResponse("Property deleted successfully", null, null, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete owner", description = "Deletes owner and all associated properties")
    public ResponseEntity<Map<String, Object>> deleteOwner(@PathVariable("id") long id) {
        boolean isDeleted = ownerService.deleteOwner(id);
        if (isDeleted) {
            return buildResponse("Owner deleted successfully", null, null, HttpStatus.NO_CONTENT);
        } else {
            return buildResponse("Owner not found", null, null, HttpStatus.NOT_FOUND);
        }
    }
}
