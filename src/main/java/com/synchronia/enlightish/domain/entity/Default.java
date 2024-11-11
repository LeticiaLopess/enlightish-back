package com.synchronia.enlightish.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synchronia.enlightish.domain.enumeration.Status;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
public class Default {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant lastUpdatedDate;

    @Version
    private Integer version;

    @PrePersist
    public void prePersist() {
        this.creationDate = Instant.now();
        this.lastUpdatedDate = this.creationDate;
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdatedDate = Instant.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Instant lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Default aDefault = (Default) o;
        return Objects.equals(id, aDefault.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
