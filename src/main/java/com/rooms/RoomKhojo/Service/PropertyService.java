package com.rooms.RoomKhojo.Service;

import com.rooms.RoomKhojo.Entity.Property;
import com.rooms.RoomKhojo.repository.PropertyRepository;
import jakarta.el.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> getAllProperty() {
        try {
            return propertyRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("In property service..."+e.getMessage());
        }

    }


    public Property getById(long id) {
        try {
            return propertyRepository.findById(id).orElseThrow(()-> new Exception("Customer not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException("error in Property service class of getByID method..."+e.getMessage());
        }
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<String> uploadImages(Long propertyId, List<MultipartFile> images) throws IOException {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));

        // Ensure uploads/images directory exists
        Path imageDir = Paths.get(uploadDir, "images");
        Files.createDirectories(imageDir);

        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile file : images) {
            validateImage(file);

            // Unique filename
            String filename = "property_" + propertyId + "_" + UUID.randomUUID() + "_" +
                    file.getOriginalFilename().replaceAll("\\s+", "_");

            Path filePath = imageDir.resolve(filename);
            Files.write(filePath, file.getBytes());

            // Save relative path for DB
            String imagePath = "/images/" + filename;
            imagePaths.add(imagePath);
        }

        // Add new images to property and save
        property.getImages().addAll(imagePaths);
        propertyRepository.save(property);

        return imagePaths;
    }

    private void validateImage(MultipartFile file) throws IOException {
        if (file.getSize() > 5_000_000) {
            throw new IOException("File size exceeds 5MB limit.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Invalid file type. Only images allowed.");
        }
    }

    public List<String> getImagePathsForProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));

        return property.getImages(); // Already contains list of image URLs like /images/filename.jpg
    }

    public List<Property> searchPropertiesByLocation(String state, String city) {
        return propertyRepository.searchByLocation(state, city);
    }
}
