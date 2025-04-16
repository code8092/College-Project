package com.rooms.RoomKhojo.Entity;

import jakarta.persistence.Entity;

@Entity
public class Customer extends User{

    public Customer(){
        super();
    }

    public Customer(long id, String name, String phoneNo) {
        super(id, name, phoneNo);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", phoneNo='" + getPhoneNo() + '\'' +
                '}';
    }
}
