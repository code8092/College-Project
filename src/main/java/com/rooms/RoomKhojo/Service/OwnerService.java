package com.rooms.RoomKhojo.Service;

import com.rooms.RoomKhojo.DTO.PropertyDTO;
import com.rooms.RoomKhojo.Entity.Location;
import com.rooms.RoomKhojo.Entity.Owner;
import com.rooms.RoomKhojo.Entity.Property;
import com.rooms.RoomKhojo.repository.OwnerRepository;
import com.rooms.RoomKhojo.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public Property addPropertyToOwner(Long ownerId, PropertyDTO dto) {
        // Fetch the owner entity from the database
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // Create a Location object based on the input DTO
        Location location = new Location();
        location.setState(dto.getState());
        location.setCity(dto.getCity());
        location.setStreetAddress(dto.getStreetAddress());

        // Create the Property object to be saved
        Property property = new Property();
        property.setOwner(owner);
        property.setResidentialPropertyType(dto.getResidentialPropertyType());
        property.setLocation(location);
        property.setImages(dto.getImages());

        // Save the property to the database
        return propertyRepository.save(property);  // This returns a Property entity
    }

    public List<Property> getPropertiesByOwnerId(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }

    public void deletePropertyForOwner(Long ownerId, Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (property.getOwner() != null) {
            property.getOwner().getId();
        }
        throw new RuntimeException("Owner not authorized to delete this property");

    }
}
