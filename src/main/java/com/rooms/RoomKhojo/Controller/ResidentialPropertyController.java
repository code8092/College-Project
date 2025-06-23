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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('CUSTOMER', 'OWNER','ADMIN')")

    @CrossOrigin
    @GetMapping
    @Operation(summary = "Get all properties", description = "Return all properties")
    public ResponseEntity<List<ResidentialProperty>> getAllProperties() {
        List<ResidentialProperty> properties = propertyService.getAllProperty();
        return ResponseEntity.ok(properties);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER','CUSTOMER','ADMIN')")
    @Operation(summary = "Get one property", description = "Return one property by ID")
    public ResponseEntity<ResidentialProperty> getPropertyById(@PathVariable Long id) {
        ResidentialProperty property = propertyService.getById(id);
        return ResponseEntity.ok(property);
    }

    @PreAuthorize("hasRole('OWNER')")
    @CrossOrigin
    @PutMapping("/{propertyId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long propertyId, @RequestParam PropertyStatus status) {
        propertyService.updateStatus(propertyId, status);
        return ResponseEntity.ok("Property status updated to " + status);
    }

    @PreAuthorize("hasRole('OWNER')")
    @CrossOrigin
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

    @PreAuthorize("hasRole('OWNER')")
    @CrossOrigin
    @PutMapping("/update/{id}")
    @Operation(summary = "Update a property", description = "Update property information")
    public ResponseEntity<ResidentialProperty> updateProperty(
            @PathVariable("id") long id, @RequestBody ResidentialProperty residentialProperty) {

        ResidentialProperty updatedProperty = propertyService.updateProperty(id, residentialProperty);
        return ResponseEntity.ok(updatedProperty);
    }

    @GetMapping("/{id}/images")
    @Operation(summary = "Get images", description = "Return images using property ID")
    public ResponseEntity<List<String>> getPropertyImages(@PathVariable Long id) {
        List<String> imagePaths = propertyService.getImagePathsForProperty(id);
        return ResponseEntity.ok(imagePaths);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @CrossOrigin
    @GetMapping("/search")
    @Operation(summary = "Global Search", description = "Search properties using keywords across owner and property fields")
    public ResponseEntity<List<ResidentialProperty>> globalSearch(@RequestParam("q") String query) {
        List<ResidentialProperty> results = propertyService.globalSearch(query);
        return ResponseEntity.ok(results);
    }
}
