package com.example.jsfcrud.services;

import com.example.jsfcrud.models.Student;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class StudentsService extends ApplicationService<Student> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public StudentsService() {
        super(Student.class);
    }

    @Override
    public Student find(String id) {
        return find(Integer.parseInt(id));
    }

    }
