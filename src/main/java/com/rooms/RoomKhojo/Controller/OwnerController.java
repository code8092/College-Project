package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.DTO.PropertyDTO;
import com.rooms.RoomKhojo.Entity.Property;
import com.rooms.RoomKhojo.Service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping("/{id}/properties")
    public ResponseEntity<?> addPropertyToOwner(
            @PathVariable("id") Long ownerId,
            @RequestBody PropertyDTO propertyDTO
    ) {
        Property savedProperty = ownerService.addPropertyToOwner(ownerId, propertyDTO);
        return ResponseEntity.ok(savedProperty);
    }

    @GetMapping("/{id}/properties")
    public ResponseEntity<?> getPropertiesByOwner(@PathVariable("id") Long ownerId) {
        List<Property> properties = ownerService.getPropertiesByOwnerId(ownerId);
        if (properties.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(properties);
    }

    @DeleteMapping("/{ownerId}/properties/{propertyId}")
    public ResponseEntity<String> deletePropertyForOwner(
            @PathVariable Long ownerId,
            @PathVariable Long propertyId
    ) {
        ownerService.deletePropertyForOwner(ownerId, propertyId);
        return ResponseEntity.ok("Property deleted successfully.");
    }


}
