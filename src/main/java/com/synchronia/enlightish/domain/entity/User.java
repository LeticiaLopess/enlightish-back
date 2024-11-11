package com.synchronia.enlightish.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synchronia.enlightish.domain.enumeration.Role;
import com.synchronia.enlightish.domain.enumeration.Status;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.*;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
public class User extends Default {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private String id;
    private String username;
    private String password;
    private String name;
    private String mail;
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant birthDate;

    private Status status;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ElementCollection
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    public User() {
    }

    public User(String username, String password, String name, String mail, String phone, Instant birthDate, Status status, Address address) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.birthDate = birthDate;
        this.status = status;
        this.address = address;
        this.roles = new ArrayList<>();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
