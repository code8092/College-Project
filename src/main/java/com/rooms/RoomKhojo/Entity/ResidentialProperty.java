package com.rooms.RoomKhojo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rooms.RoomKhojo.Enum.PropertyStatus;
import com.rooms.RoomKhojo.Enum.ResidentialPropertyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ResidentialProperty {

    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    @Schema(hidden = true)
    private Owner owner;

    @Enumerated(EnumType.STRING)
    private ResidentialPropertyType residentialPropertyType;

    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_path")
    private List<String> images = new ArrayList<>();

    private String roomSize;
    private float price;

    @ElementCollection
    @CollectionTable(name = "property_facilities", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "facility")
    private List<String> facility = new ArrayList<>();

    public ResidentialProperty() {}

    public ResidentialProperty(ResidentialPropertyType residentialPropertyType, Location location,
                               List<String> images, String roomSize, float price, List<String> facility) {
        this.residentialPropertyType = residentialPropertyType;
        this.location = location;
        this.images = images;
        this.roomSize = roomSize;
        this.price = price;
        this.facility = facility;
        this.propertyStatus = PropertyStatus.AVAILABLE; // default status
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public List<String> getFacility() {
        return facility;
    }

    public void setFacility(List<String> facility) {
        this.facility = facility;
    }

    @Override
    public String toString() {
        return "ResidentialProperty{" +
                "id=" + id +
                ", owner=" + owner +
                ", residentialPropertyType=" + residentialPropertyType +
                ", propertyStatus=" + propertyStatus +
                ", location=" + location +
                ", images=" + images +
                ", roomSize='" + roomSize + '\'' +
                ", price=" + price +
                ", facility=" + facility +
                '}';
    }
}
