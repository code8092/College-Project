package com.rooms.RoomKhojo.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class  Owner extends User{

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Property> properties = new ArrayList<>();

    public Owner(){
        super();
    }

    public Owner(long id, String name, String phoneNo, List<Property> properties) {
        super(id, name, phoneNo);
        this.properties = properties;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    //adding property
    public void addProperty(Property property){
        properties.add(property);
        property.setOwner(this);
    }

    //removing property
    public void removeProperty(Property property){
        properties.remove(property);
        property.setOwner(null);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", phoneNo='" + getPhoneNo() + '\'' +
                ", propertiesCount=" + properties.size() +
                '}';
    }
}
