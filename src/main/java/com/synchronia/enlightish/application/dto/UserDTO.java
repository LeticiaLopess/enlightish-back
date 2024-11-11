package com.synchronia.enlightish.application.dto;

import com.synchronia.enlightish.domain.enumeration.Role;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class UserDTO implements Serializable {

    private String id;
    private String username;
    private String name;
    private String mail;
    private String phone;
    private Instant birthDate;
    private List<Role> roles;

    public UserDTO() {}

    public UserDTO(String id, String username, String name, String mail, String phone, Instant birthDate, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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