package com.rooms.RoomKhojo.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String name;
    private String phoneNo;

    public User(){}

    public User(long id, String name, String phoneNo) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                '}';
    }
}
