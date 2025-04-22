package com.rooms.RoomKhojo.DTO;

import com.rooms.RoomKhojo.Enum.ResidentialPropertyType;

import java.util.List;

public class PropertyDTO {

    private ResidentialPropertyType residentialPropertyType;
    private String state;
    private String city;
    private String streetAddress;
    private List<String> images;

    public PropertyDTO() {}

    public ResidentialPropertyType getResidentialPropertyType() {
        return residentialPropertyType;
    }

    public void setResidentialPropertyType(ResidentialPropertyType residentialPropertyType) {
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
}
