package com.example.jsfcrud.models;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Student extends ApplicationRecord {

    @NotBlank
    private String name;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Student() {
    }

    //<editor-fold defaultstate="collapsed" desc="Get/Set">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    //</editor-fold>
}
