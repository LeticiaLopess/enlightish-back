package com.synchronia.enlightish.application.dto;
import java.io.Serializable;
import java.time.Instant;

public class LeadDTO implements Serializable {

    private String id;
    private String name;
    private String mail;
    private String phone;
    private Instant birth;

    public LeadDTO() {}

    public LeadDTO(String id, String name, String mail, String phone, Instant birth) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.birth = birth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Instant getBirth() {
        return birth;
    }

    public void setBirth(Instant birth) {
        this.birth = birth;
    }
}