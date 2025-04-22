package com.rooms.RoomKhojo.Entity;
import com.rooms.RoomKhojo.Enum.ResidentialPropertyType;
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
    private ResidentialPropertyType residentialPropertyType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @ElementCollection
    //@ElementCollection to store the list of image paths
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_path")
    private List<String> images = new ArrayList<>();

    public Property(){}
//constructor
//    public Property(long id, Owner owner, ResidentialPropertyType residentialPropertyType, String location, List<String> images) {
//        this.id = id;
//        this.owner = owner;
//        this.residentialPropertyType = residentialPropertyType;
//        this.location = location;
//        this.images = images;
//    }


    public Property(long id, Owner owner, ResidentialPropertyType residentialPropertyType, Location location, List<String> images) {
        this.id = id;
        this.owner = owner;
        this.residentialPropertyType = residentialPropertyType;
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

    public ResidentialPropertyType getResidentialPropertyType() {
        return residentialPropertyType;
    }

    public void setResidentialPropertyType(ResidentialPropertyType residentialPropertyType) {
        this.residentialPropertyType = residentialPropertyType;
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

    //    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public Owner getOwner() {
//        return owner;
//    }
//
//    public void setOwner(Owner owner) {
//        this.owner = owner;
//    }
//
//    public ResidentialPropertyType getPropertyType() {
//        return residentialPropertyType;
//    }
//
//    public void setPropertyType(ResidentialPropertyType residentialPropertyType) {
//        this.residentialPropertyType = residentialPropertyType;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public List<String> getImages() {
//        return images;
//    }
//
//    public void setImages(List<String> images) {
//        this.images = images;
//    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", owner=" + owner +
                ", residentialPropertyType=" + residentialPropertyType +
                ", location=" + location +
                ", images=" + images +
                '}';
    }

}
