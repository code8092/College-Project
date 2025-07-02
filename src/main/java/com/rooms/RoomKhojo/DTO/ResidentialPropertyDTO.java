package com.rooms.RoomKhojo.DTO;

import com.rooms.RoomKhojo.Enum.PropertyStatus;
import com.rooms.RoomKhojo.Enum.ResidentialPropertyType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResidentialPropertyDTO {

    private Long ownerId;

    @NotNull
    private ResidentialPropertyType residentialPropertyType;

    @NotBlank(message = "Enter valid state")
    private String state;

    @NotBlank(message = "Enter valid city")
    private String city;

    @NotBlank(message = "invalid zipcode")
    private String zipCode;


    private String streetAddress;

    private List<String> images;

    @NotEmpty(message = "enter roomSize")
    private String roomSize;

    @Min(value = 1, message = "Enter a valid price greater than 0")
    private float price;




    @NotEmpty(message = "please enter one or more facilities")
    private List<String> facilities;

    private PropertyStatus status;

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }


    public ResidentialPropertyDTO() {
        residentialPropertyType = null;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public @NotNull ResidentialPropertyType getResidentialPropertyType() {
        return residentialPropertyType;
    }

    public void setResidentialPropertyType(@NotNull ResidentialPropertyType residentialPropertyType) {
        this.residentialPropertyType = residentialPropertyType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }
}
