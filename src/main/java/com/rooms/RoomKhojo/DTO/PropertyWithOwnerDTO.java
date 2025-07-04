package com.rooms.RoomKhojo.DTO;

import com.rooms.RoomKhojo.Entity.Location;
import com.rooms.RoomKhojo.Entity.ResidentialProperty;
import com.rooms.RoomKhojo.Enum.PropertyStatus;
import com.rooms.RoomKhojo.Enum.ResidentialPropertyType;

import java.util.List;

public class PropertyWithOwnerDTO {

    // Property Info
    private Long propertyId;
    private ResidentialPropertyType residentialPropertyType;
    private PropertyStatus propertyStatus;
    private String roomSize;
    private float price;
    private List<String> images;
    private List<String> facilities;

    // Location Info
    private String city;
    private String state;
    private String zipCode;
    private String streetAddress;

    // Owner Info
    private Long ownerId;
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;

    // Constructor using entity
    public PropertyWithOwnerDTO(ResidentialProperty property) {
        this.propertyId = property.getId();
        this.residentialPropertyType = property.getResidentialPropertyType();
        this.propertyStatus = property.getPropertyStatus();
        this.roomSize = property.getRoomSize();
        this.price = property.getPrice();
        this.images = property.getImages();
        this.facilities = property.getFacility();

        if (property.getLocation() != null) {
            Location location = property.getLocation();
            this.city = location.getCity();
            this.state = location.getState();
            this.zipCode = location.getZipCode();
            this.streetAddress = location.getStreetAddress();
        }

        if (property.getOwner() != null) {
            this.ownerId = property.getOwner().getId();
            this.ownerName = property.getOwner().getName();
            this.ownerEmail = property.getOwner().getEmail();
            this.ownerPhone = property.getOwner().getPhoneNo();
        }
    }

    // Getters and Setters

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public ResidentialPropertyType getResidentialPropertyType() {
        return residentialPropertyType;
    }

    public void setResidentialPropertyType(ResidentialPropertyType residentialPropertyType) {
        this.residentialPropertyType = residentialPropertyType;
    }

    public PropertyStatus getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(PropertyStatus propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }
}
