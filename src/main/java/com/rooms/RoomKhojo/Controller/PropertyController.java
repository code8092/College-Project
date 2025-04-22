package com.rooms.RoomKhojo.Controller;
import com.rooms.RoomKhojo.Entity.Property;
import com.rooms.RoomKhojo.Service.PropertyService;
import jakarta.el.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @GetMapping("/properties")
    public List<Property> getAllProperty(){
        try {
            return propertyService.getAllProperty();
        } catch (RuntimeException e) {
            throw new RuntimeException("In Property controller of Get all properties.."+e.getMessage());
        }
    }

    @GetMapping("/properties/{id}")
    public Property getById(@PathVariable("id") long id){
        try {
            return propertyService.getById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error in Property Controller of getById method.."+e.getMessage());
        }
    }

    // Upload images to a property
    @PostMapping("/{id}/upload-images")
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long propertyId,
            @RequestParam("images") List<MultipartFile> imageFiles) {

        try {
            List<String> uploadedPaths = propertyService.uploadImages(propertyId, imageFiles);
            return ResponseEntity.ok(uploadedPaths);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error uploading images: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<?> getPropertyImages(@PathVariable("id") Long propertyId) {
        try {
            List<String> imagePaths = propertyService.getImagePathsForProperty(propertyId);
            return ResponseEntity.ok(imagePaths);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/properties/search to search properties by location (state, city, or zip code)
    @GetMapping("/search")
    public ResponseEntity<List<Property>> searchProperties(
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "city", required = false) String city) {

        List<Property> properties = propertyService.searchPropertiesByLocation(state, city);
        return ResponseEntity.ok(properties);
    }

}
