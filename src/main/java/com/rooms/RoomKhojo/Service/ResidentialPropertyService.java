package com.rooms.RoomKhojo.Service;

import com.rooms.RoomKhojo.Entity.Customer;
import com.rooms.RoomKhojo.Entity.ResidentialProperty;
import com.rooms.RoomKhojo.Enum.PropertyStatus;
import com.rooms.RoomKhojo.repository.ResidentialPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ResidentialPropertyService {

    @Autowired
    private ResidentialPropertyRepository residentialPropertyRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;


    public List<ResidentialProperty> getAllProperty() {
        return residentialPropertyRepository.findAll();
    }

    public ResidentialProperty getById(long id) {
        return residentialPropertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
    }

    public void updateStatus(Long propertyId, PropertyStatus status) {
        ResidentialProperty property = residentialPropertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        property.setPropertyStatus(status);
        residentialPropertyRepository.save(property);
    }


    public List<String> uploadImages(Long propertyId, List<MultipartFile> images) throws IOException {
        ResidentialProperty property = residentialPropertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));

        Path imageDir = Paths.get(uploadDir, "images");
        Files.createDirectories(imageDir);

        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile file : images) {
            validateImage(file);

            String filename = "property_" + propertyId + "_" + UUID.randomUUID() + "_" +
                    file.getOriginalFilename().replaceAll("\\s+", "_");

            Path filePath = imageDir.resolve(filename);
            Files.write(filePath, file.getBytes());

            imagePaths.add("/images/" + filename);
        }

        property.getImages().addAll(imagePaths);
        residentialPropertyRepository.save(property);

        return imagePaths;
    }

    private void validateImage(MultipartFile file) throws IOException {
        if (file.getSize() > 5_000_000) {
            throw new IOException("File size exceeds 5MB limit.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Invalid file type. Only image files are allowed.");
        }
    }

    public List<String> getImagePathsForProperty(Long propertyId) {
        ResidentialProperty property = residentialPropertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));

        return property.getImages();
    }

    public List<ResidentialProperty> searchPropertiesByLocation(String state, String city) {
        return residentialPropertyRepository.searchByLocation(state, city);
    }

    public ResidentialProperty updateProperty(long id, ResidentialProperty residentialProperty) {
        ResidentialProperty existingProperty = residentialPropertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));

        existingProperty.setLocation(residentialProperty.getLocation());
        existingProperty.setResidentialPropertyType(residentialProperty.getResidentialPropertyType());
        existingProperty.setFacility(residentialProperty.getFacility());
        existingProperty.setImages(residentialProperty.getImages());
        existingProperty.setRoomSize(residentialProperty.getRoomSize());
        existingProperty.setPrice(residentialProperty.getPrice());

        return residentialPropertyRepository.save(existingProperty);
    }
}
