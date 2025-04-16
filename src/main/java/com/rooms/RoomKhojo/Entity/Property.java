package com.rooms.RoomKhojo.Entity;

import com.rooms.RoomKhojo.Enum.PropertyType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Enumerated(EnumType.STRING)
    //@Enumerated for the PropertyType enum field
    private PropertyType propertyType;

    private String location;

    @ElementCollection
    //@ElementCollection to store the list of image paths
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_path")
    private List<String> images = new ArrayList<>();

    public Property(){}

    public Property(long id, Owner owner, PropertyType propertyType, String location, List<String> images) {
        this.id = id;
        this.owner = owner;
        this.propertyType = propertyType;
        this.location = location;
        this.images = images;
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

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", owner=" + owner +
                ", propertyType=" + propertyType +
                ", location='" + location + '\'' +
                ", images=" + images +
                '}';
    }
}
