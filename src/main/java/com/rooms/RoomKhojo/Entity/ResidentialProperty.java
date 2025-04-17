package com.rooms.RoomKhojo.Entity;

import com.rooms.RoomKhojo.Enum.ResidentialPropertyType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ResidentialProperty extends Property{

    private String roomSize;

    @ElementCollection
    @CollectionTable(name = "property_facilities", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "facility")
    List<String> facility = new ArrayList<>();


    public ResidentialProperty(){}


    public ResidentialProperty(long id, Owner owner, ResidentialPropertyType residentialPropertyType, Location location, List<String> images, String roomSize, List<String> facility) {
        super(id, owner, residentialPropertyType, location, images);
        this.roomSize = roomSize;
        this.facility = facility;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public List<String> getFacilities() {
        return facility;
    }

    public void setFacilities(List<String> facilities) {
        this.facility = facilities;
    }

    @Override
    public String toString() {
        return "ResidentialProperty{" +
                "id=" + getId() +
                ", owner=" + (getOwner() != null ? getOwner().getName() : "none") +
                ", propertyType=" + getResidentialPropertyType() +
                ", location=" + getLocation() +
                ", roomSize='" + roomSize + '\'' +
                ", facilities=" + facility +
                ", imagesCount=" + (getImages() != null ? getImages().size() : 0) +
                '}';
    }

}
