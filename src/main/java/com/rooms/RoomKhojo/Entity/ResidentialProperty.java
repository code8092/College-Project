package com.rooms.RoomKhojo.Entity;

import com.rooms.RoomKhojo.Enum.PropertyType;
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

    public ResidentialProperty(String roomSize, List<String> facality) {
        this.roomSize = roomSize;
        this.facility = facality;
    }

    public ResidentialProperty(long id, Owner owner, PropertyType propertyType, String location, List<String> images, String roomSize, List<String> facality) {
        super(id, owner, propertyType, location, images);
        this.roomSize = roomSize;
        this.facility = facality;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public List<String> getFacality() {
        return facility;
    }

    public void setFacality(List<String> facality) {
        this.facility = facality;
    }

    @Override
    public String toString() {
        return "ResidentialProperty{" +
                "id=" + getId() +
                ", owner=" + (getOwner() != null ? getOwner().getName() : "none") +
                ", propertyType=" + getPropertyType() +
                ", location='" + getLocation() + '\'' +
                ", roomSize='" + roomSize + '\'' +
                ", facilities=" + facility +
                ", imagesCount=" + getImages().size() +
                '}';
    }
}
