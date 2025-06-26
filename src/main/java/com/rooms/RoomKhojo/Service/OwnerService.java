package com.rooms.RoomKhojo.Service;

import com.rooms.RoomKhojo.DTO.ResidentialPropertyDTO;
import com.rooms.RoomKhojo.Entity.Location;
import com.rooms.RoomKhojo.Entity.Owner;
import com.rooms.RoomKhojo.Entity.ResidentialProperty;
import com.rooms.RoomKhojo.repository.OwnerRepository;
import com.rooms.RoomKhojo.repository.ResidentialPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ResidentialPropertyRepository propertyRepository;

    public ResidentialProperty addPropertyToOwner(Long ownerId, ResidentialPropertyDTO dto) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + ownerId));

        Location location = new Location();
        location.setState(dto.getState());
        location.setCity(dto.getCity());
        location.setStreetAddress(dto.getStreetAddress());
        location.setZipCode(dto.getZipCode());

        ResidentialProperty property = new ResidentialProperty();
        property.setOwner(owner);
        property.setResidentialPropertyType(dto.getResidentialPropertyType());
        property.setLocation(location);
        property.setImages(dto.getImages());
        property.setRoomSize(dto.getRoomSize());
        property.setPrice(dto.getPrice());
        property.setFacility(dto.getFacilities());

        return propertyRepository.save(property);
    }

    public List<ResidentialProperty> getPropertiesByOwnerId(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }

    public void deletePropertyForOwner(Long ownerId, Long propertyId) {
        ResidentialProperty property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));

        if (property.getOwner() != null) {
            property.getOwner().getId();
        }
        else {
            throw new RuntimeException("Owner not authorized to delete this property");
        }
        propertyRepository.delete(property);
    }

    public Owner saveOwner(Owner owner) {
        Optional<Owner> existingByEmail = ownerRepository.findByEmail(owner.getEmail());
        if (existingByEmail.isPresent()) {
            throw new RuntimeException("Owner with this email already exists");
        }

        return ownerRepository.save(owner);
    }

    public Owner updateOwner(long ownerId, Owner owner) {
        Owner existingOwner = ownerRepository.findById(ownerId).
                orElseThrow(()-> new RuntimeException("Owner not found by Id"+ownerId));
        existingOwner.setName(owner.getName());
        existingOwner.setEmail(owner.getEmail());
        existingOwner.setPhoneNo(owner.getPhoneNo());
        existingOwner.setGender(owner.getGender());
        existingOwner.setPassword(owner.getPassword());
        return ownerRepository.save(existingOwner);
    }

    public boolean deleteOwner(long id) {
        List<ResidentialProperty> properties = propertyRepository.findByOwnerId(id);

        if(!properties.isEmpty()){
            propertyRepository.deleteAll(properties);
            System.out.println("Owner properties deleted successfully..");
        }

        if(ownerRepository.existsById(id)){
            ownerRepository.deleteById(id);
            System.out.println("owner deleted successfully");
            return true;
        }
        return false;
    }

    public List<Owner> getAllOwner() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> getOne(Long id) {
        return ownerRepository.findById(id);
    }
}
