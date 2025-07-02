package com.rooms.RoomKhojo.Controller;

import com.rooms.RoomKhojo.Entity.ResidentialProperty;
import com.rooms.RoomKhojo.Enum.PropertyStatus;
import com.rooms.RoomKhojo.Security.JwtUtil;
import com.rooms.RoomKhojo.Service.ResidentialPropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/properties")
@Tag(name = "Property API", description = "Property related operations")
@CrossOrigin
public class ResidentialPropertyController {

    @Autowired
    private ResidentialPropertyService propertyService;

    @Autowired
    private JwtUtil jwtUtil;

    // Helper for consistent JSON response
    private ResponseEntity<Map<String, Object>> buildResponse(String message, Object body, Map<String, String> errors, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("body", body);
        response.put("errors", errors);
        response.put("status", status.value());
        return new ResponseEntity<>(response, status);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'OWNER', 'ADMIN')")
    @GetMapping
    @Operation(summary = "Get all properties", description = "Return all properties")
    public ResponseEntity<Map<String, Object>> getAllProperties() {
        List<ResidentialProperty> properties = propertyService.getAllProperty();
        return buildResponse("Properties retrieved successfully", properties, null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'CUSTOMER', 'ADMIN')")
    @Operation(summary = "Get one property", description = "Return one property by ID")
    public ResponseEntity<Map<String, Object>> getPropertyById(@PathVariable Long id) {
        ResidentialProperty property = propertyService.getById(id);
        return buildResponse("Property retrieved successfully", property, null, HttpStatus.OK);
    }

    @PutMapping("/{propertyId}/status")
    @PreAuthorize("hasRole('OWNER')")
    @Operation(summary = "Update property status", description = "Change property status (e.g., AVAILABLE, BOOKED)")
    public ResponseEntity<Map<String, Object>> updateStatus(@PathVariable Long propertyId, @RequestParam PropertyStatus status) {
        propertyService.updateStatus(propertyId, status);
        return buildResponse("Property status updated", status.name(), null, HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('OWNER')")
    @Operation(summary = "Upload images", description = "Upload multiple images for a property by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Images uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Map<String, Object>> uploadImages(
            @PathVariable Long id,
            @RequestPart("images") List<MultipartFile> imageFiles) {

        try {
            List<String> uploadedPaths = propertyService.uploadImages(id, imageFiles);
            return buildResponse("Images uploaded successfully", uploadedPaths, null, HttpStatus.OK);
        } catch (IOException e) {
            Map<String, String> error = new HashMap<>();
            error.put("exception", e.getMessage());
            return buildResponse("Error uploading images", null, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Map<String, Object>> updateProperty(
            @PathVariable("id") long propertyId,
            @RequestBody ResidentialProperty residentialProperty,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        Long ownerIdFromToken = jwtUtil.getOwnerIdFromToken(token);

        // Validate ownership
        if (!propertyService.isOwnerOfProperty(ownerIdFromToken, propertyId)) {
            return buildResponse("Access denied: You are not the owner of this property", null, null, HttpStatus.FORBIDDEN);
        }

        ResidentialProperty updatedProperty = propertyService.updateProperty(propertyId, residentialProperty, ownerIdFromToken);

        return buildResponse("Property updated successfully", updatedProperty, null, HttpStatus.OK);
    }


    @GetMapping("/{id}/images")
    @Operation(summary = "Get property images", description = "Return image paths using property ID")
    public ResponseEntity<Map<String, Object>> getPropertyImages(@PathVariable Long id) {
        List<String> imagePaths = propertyService.getImagePathsForProperty(id);
        return buildResponse("Images retrieved successfully", imagePaths, null, HttpStatus.OK);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @Operation(summary = "Global Search", description = "Search properties using keywords across owner and property fields")
    public ResponseEntity<Map<String, Object>> globalSearch(@RequestParam("q") String query) {
        List<ResidentialProperty> results = propertyService.globalSearch(query);
        return buildResponse("Search results retrieved", results, null, HttpStatus.OK);
    }
}
