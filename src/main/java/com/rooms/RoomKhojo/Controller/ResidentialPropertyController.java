package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.Entity.ResidentialProperty;
import com.rooms.RoomKhojo.Enum.PropertyStatus;
import com.rooms.RoomKhojo.Service.ResidentialPropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/properties")
@Tag(name = "Property API", description = "Property related operation")
public class ResidentialPropertyController {

    @Autowired
    private ResidentialPropertyService propertyService;

    @GetMapping
    @Operation(summary = "Get all properties", description = "Return all properties")
    public ResponseEntity<List<ResidentialProperty>> getAllProperties() {
        List<ResidentialProperty> properties = propertyService.getAllProperty();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one property", description = "Return one property by ID")
    public ResponseEntity<ResidentialProperty> getPropertyById(@PathVariable Long id) {
        ResidentialProperty property = propertyService.getById(id);
        return ResponseEntity.ok(property);
    }

    @PutMapping("/{propertyId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long propertyId, @RequestParam PropertyStatus status) {
        propertyService.updateStatus(propertyId, status);
        return ResponseEntity.ok("Property status updated to " + status);
    }

    @PostMapping(path = "/{id}/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload images", description = "Upload multiple images for a property by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Images uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> uploadImages(
            @PathVariable Long id,
            @RequestPart("images") List<MultipartFile> imageFiles) {

        try {
            List<String> uploadedPaths = propertyService.uploadImages(id, imageFiles);
            return ResponseEntity.ok(uploadedPaths);
        } catch (IOException e) {
            // This is a checked exception, so it's fine to handle it here
            return ResponseEntity.internalServerError().body("Error uploading images: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a property", description = "Update property information")
    public ResponseEntity<ResidentialProperty> updateProperty(
            @PathVariable("id") long id, @RequestBody ResidentialProperty residentialProperty) {

        ResidentialProperty updatedProperty = propertyService.updateProperty(id, residentialProperty);
        return ResponseEntity.ok(updatedProperty); // If not found, throw and handle globally
    }

    @GetMapping("/{id}/images")
    @Operation(summary = "Get images", description = "Return images using property ID")
    public ResponseEntity<List<String>> getPropertyImages(@PathVariable Long id) {
        List<String> imagePaths = propertyService.getImagePathsForProperty(id);
        return ResponseEntity.ok(imagePaths);
    }

    @GetMapping("/search")
    @Operation(summary = "Search by location", description = "Search property by state or city or both")
    public ResponseEntity<List<ResidentialProperty>> searchPropertiesByLocation(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String city) {

        List<ResidentialProperty> properties = propertyService.searchPropertiesByLocation(state, city);
        return ResponseEntity.ok(properties);
    }
}
