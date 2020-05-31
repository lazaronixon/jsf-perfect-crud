package com.example.jsfcrud.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Student extends ApplicationRecord<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name can't be blank")
    private String name;

    private String address;

    public Student() {
    }

    @Override
    public String getEditPath() {
        return "/views/students/edit.xhtml?id=" + getId();
    }

    @Override
    public String getPath() {
        return "/views/students/show.xhtml?id=" + getId();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Get/Set">    
    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

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
    //</editor-fold>
}
