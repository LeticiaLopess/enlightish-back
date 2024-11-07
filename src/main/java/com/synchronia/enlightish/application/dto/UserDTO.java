package com.synchronia.enlightish.application.dto;

import com.synchronia.enlightish.domain.enumeration.Role;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UserDTO {

    private UUID id;
    private String username;
    private String name;
    private String mail;
    private String phoneNumber;
    private Instant birthDate;
    private List<Role> roles;

    public UserDTO() {}

    public UserDTO(UUID id, String username, String name, String mail, String phoneNumber, Instant birthDate, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}