package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.DTO.ResidentialPropertyDTO;
import com.rooms.RoomKhojo.Entity.Owner;
import com.rooms.RoomKhojo.Entity.ResidentialProperty;
import com.rooms.RoomKhojo.Service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Owner")
@Tag(name = "Owner API", description = "Operation related to Owner API")
@CrossOrigin
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping("/save")
    @Operation(summary = "Save an owner", description = "This method saves an owner")
    public ResponseEntity<Owner> saveOwner(@Valid @RequestBody Owner owner) {
        Owner savedOwner = ownerService.saveOwner(owner);
        return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/properties")
    @Operation(summary = "Add property to owner", description = "This adds a property to an owner by ID")
    public ResponseEntity<String> addPropertyToOwner(
            @PathVariable("id") Long ownerId,
            @Valid @RequestBody ResidentialPropertyDTO residentialPropertyDTO) {
        ResidentialProperty savedProperty = ownerService.addPropertyToOwner(ownerId, residentialPropertyDTO);
        return ResponseEntity.ok("Property added successfully for Owner ID " + ownerId + ", Property ID " + savedProperty.getId());
    }

    @GetMapping("/{id}/properties")
    @Operation(summary = "Get properties by owner ID", description = "Owner retrieves their properties by ID")
    public ResponseEntity<List<ResidentialProperty>> getPropertiesByOwner(@PathVariable("id") Long ownerId) {
        List<ResidentialProperty> properties = ownerService.getPropertiesByOwnerId(ownerId);
        return properties.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(properties);
    }

    @GetMapping
    @Operation(summary = "Get all owners", description = "Returns all owners")
    public ResponseEntity<List<Owner>> getAllOwner() {
        List<Owner> owners = ownerService.getAllOwner();
        return owners.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(owners);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one customer by his id", description = "Returns one customer")
    public HttpEntity<Optional<Owner>> getOne(@PathVariable("id") Long id){
        Optional<Owner> owner = ownerService.getOne(id);
        return new ResponseEntity<>(owner,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update owner", description = "Owner updates name, phone, or email")
    public ResponseEntity<Owner> updateOwner(
            @PathVariable("id") long ownerId,
            @RequestBody Owner owner) {
        Owner updatedOwner = ownerService.updateOwner(ownerId, owner);
        return ResponseEntity.ok(updatedOwner); // Let service throw if not found
    }

    @DeleteMapping("/{ownerId}/properties/{propertyId}")
    @Operation(summary = "Delete property", description = "Owner deletes property by their ID and property ID")
    public ResponseEntity<String> deletePropertyForOwner(
            @PathVariable Long ownerId,
            @PathVariable Long propertyId) {
        ownerService.deletePropertyForOwner(ownerId, propertyId);
        return ResponseEntity.ok("Property deleted successfully.");
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete owner", description = "Deletes owner and all associated properties")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") long id) {
        boolean isDeleted = ownerService.deleteCustomer(id);
        return isDeleted ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
