package com.rooms.RoomKhojo.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Customer")
public class Customer {
    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    @Column(unique = true)
    private String name;

    @NotEmpty(message = "password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;


    @Pattern(
            regexp = "^(\\+91|91|0)?[6-9]\\d{9}$",
            message = "Invalid Indian phone number. Must be 10 digits starting with 6-9, with optional +91 or 0."
    )
    private String phoneNo;

    @Email(message = "email is not valid")
    @NotEmpty(message = "email is required")
    private String email;

    @NotEmpty(message = "Please enter gender")
    private String gender;

    public Customer() {
    }

    public Customer(String name, String password, String phoneNo, String email, String gender) {
        this.name = name;
        this.password = password;
        this.phoneNo = phoneNo;
        this.email = email;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeneder() {
        return gender;
    }

    public void setGeneder(String geneder) {
        this.gender = geneder;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

}
