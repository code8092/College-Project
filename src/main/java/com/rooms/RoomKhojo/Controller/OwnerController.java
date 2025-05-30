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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@PreAuthorize("hasRole('OWNER')")
@RequestMapping("/Owner")
@Tag(name = "Owner API", description = "Operation related to Owner API")
@CrossOrigin
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @CrossOrigin
    @PostMapping
    @Operation(summary = "Create an owner", description = "This method create an owner")
    public ResponseEntity<Owner> saveOwner(@Valid @RequestBody Owner owner) {
        Owner savedOwner = ownerService.saveOwner(owner);
        return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    @Operation(summary = "Add property to owner", description = "This adds a property to an owner by ID")
    public ResponseEntity<String> addPropertyToOwner(
            @PathVariable("id") Long ownerId,
            @Valid @RequestBody ResidentialPropertyDTO residentialPropertyDTO) {
        ResidentialProperty savedProperty = ownerService.addPropertyToOwner(ownerId, residentialPropertyDTO);
        return ResponseEntity.ok("Property added successfully for Owner ID " + ownerId + ", Property ID " + savedProperty.getId());
    }

    @CrossOrigin
    @GetMapping("/{id}/properties")
    @PreAuthorize("hasRole('OWNER')")
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

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get one owner by his id", description = "Returns one owner")
    public HttpEntity<Optional<Owner>> getOne(@PathVariable("id") Long id){
        Optional<Owner> owner = ownerService.getOne(id);
        return new ResponseEntity<>(owner,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('OWNER')")
    @CrossOrigin
    @PutMapping("/{id}")
    @Operation(summary = "Update owner", description = "Owner updates name, phone, or email")
    public ResponseEntity<Owner> updateOwner(
            @PathVariable("id") long ownerId,
            @RequestBody Owner owner) {
        Owner updatedOwner = ownerService.updateOwner(ownerId, owner);
        return ResponseEntity.ok(updatedOwner); // Let service throw if not found
    }

    @PreAuthorize("hasRole('OWNER')")
    @CrossOrigin
    @DeleteMapping("/{ownerId}/properties/{propertyId}")
    @Operation(summary = "Delete property", description = "Owner deletes property by their ID and property ID")
    public ResponseEntity<String> deletePropertyForOwner(
            @PathVariable Long ownerId,
            @PathVariable Long propertyId) {
        ownerService.deletePropertyForOwner(ownerId, propertyId);
        return ResponseEntity.ok("Property deleted successfully.");
    }

    @PreAuthorize("hasRole('OWNER')")
    @CrossOrigin
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete owner", description = "Deletes owner and all associated properties")
    public ResponseEntity<Void> deleteOwner(@PathVariable("id") long id) {
        boolean isDeleted = ownerService.deleteOwner(id);
        return isDeleted ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
