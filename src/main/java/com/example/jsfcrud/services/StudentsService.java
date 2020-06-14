package com.example.jsfcrud.services;

import com.example.jsfcrud.models.Student;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class StudentsService extends ApplicationService<Student> {

    @PersistenceContext(unitName = "jsfcrud")
    private EntityManager em;

    public StudentsService() {
        super(Student.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Student find(String id) {
        return getEntityManager().find(Student.class, Integer.parseInt(id));
    }
}
