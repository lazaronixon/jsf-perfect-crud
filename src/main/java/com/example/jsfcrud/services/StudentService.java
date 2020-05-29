package com.example.jsfcrud.services;

import com.example.jsfcrud.models.Student;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class StudentService extends ApplicationService<Student> implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "jsfcrud")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentService() {
        super(Student.class);
    }
}
