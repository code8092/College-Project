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
@Tag(name = "Owner API", description = "Operations related to Owner management")
@CrossOrigin
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Helper method for consistent response
    private ResponseEntity<Map<String, Object>> buildResponse(String message, Object body, Map<String, String> errors, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("body", body);
        response.put("errors", errors);
        response.put("status", status.value());
        return new ResponseEntity<>(response, status);
    }

    @PostMapping
    @Operation(summary = "Create an Owner", description = "Registers a new owner")
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

    @PostMapping("/property")
    @PreAuthorize("hasRole('OWNER')")
    @Operation(summary = "Add Property", description = "Adds a residential property for the logged-in owner")
    public ResponseEntity<Map<String, Object>> addPropertyToOwner(
            @Valid @RequestBody ResidentialPropertyDTO residentialPropertyDTO,
            @RequestHeader("Authorization") String authHeader) {

        Long ownerId = jwtUtil.getOwnerIdFromToken(authHeader.substring(7));
        ResidentialProperty savedProperty = ownerService.addPropertyToOwner(ownerId, residentialPropertyDTO);
        return buildResponse("Property added successfully", savedProperty, null, HttpStatus.OK);
    }

    @GetMapping("/properties")
    @PreAuthorize("hasRole('OWNER')")
    @Operation(summary = "Get Properties", description = "Retrieves all properties for the logged-in owner")
    public ResponseEntity<Map<String, Object>> getPropertiesByOwner(@RequestHeader("Authorization") String authHeader) {
        Long ownerId = jwtUtil.getOwnerIdFromToken(authHeader.substring(7));
        List<ResidentialProperty> properties = ownerService.getPropertiesByOwnerId(ownerId);
        return buildResponse("Properties retrieved successfully", properties, null, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get All Owners", description = "Fetches all registered owners")
    public ResponseEntity<Map<String, Object>> getAllOwner() {
        List<Owner> owners = ownerService.getAllOwner();
        if (owners.isEmpty()) {
            return buildResponse("No owners found", Collections.emptyList(), null, HttpStatus.NO_CONTENT);
        }
        return buildResponse("Owners retrieved successfully", owners, null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @Operation(summary = "Get Owner By ID", description = "Fetches a single owner by ID")
    public ResponseEntity<Map<String, Object>> getOne(@PathVariable("id") Long id) {
        Optional<Owner> owner = ownerService.getOne(id);
        return owner
                .map(value -> buildResponse("Owner retrieved successfully", value, null, HttpStatus.OK))
                .orElseGet(() -> buildResponse("Owner not found", null, null, HttpStatus.NOT_FOUND));
    }

    @PutMapping
    @PreAuthorize("hasRole('OWNER')")
    @Operation(summary = "Update Owner", description = "Updates the logged-in owner's details")
    public ResponseEntity<Map<String, Object>> updateOwner(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Owner ownerDetails) {

        Long ownerId = jwtUtil.getOwnerIdFromToken(authHeader.substring(7));
        Owner updatedOwner = ownerService.updateOwner(ownerId, ownerDetails);
        return buildResponse("Owner updated successfully", updatedOwner, null, HttpStatus.OK);
    }

    @DeleteMapping("/properties/{propertyId}")
    @PreAuthorize("hasRole('OWNER')")
    @Operation(summary = "Delete Property", description = "Deletes a specific property of the logged-in owner")
    public ResponseEntity<Map<String, Object>> deletePropertyForOwner(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long propertyId) {

        Long ownerId = jwtUtil.getOwnerIdFromToken(authHeader.substring(7));
        ownerService.deletePropertyForOwner(ownerId, propertyId);
        return buildResponse("Property deleted successfully", null, null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete Owner", description = "Deletes an owner and all their associated properties")
    public ResponseEntity<Map<String, Object>> deleteOwner(@PathVariable("id") long id) {
        boolean isDeleted = ownerService.deleteOwner(id);
        if (isDeleted) {
            return buildResponse("Owner deleted successfully", null, null, HttpStatus.NO_CONTENT);
        } else {
            return buildResponse("Owner not found", null, null, HttpStatus.NOT_FOUND);
        }
    }
}
