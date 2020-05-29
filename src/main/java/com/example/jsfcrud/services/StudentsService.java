package com.example.jsfcrud.services;

import com.example.jsfcrud.models.Student;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class StudentsService extends ApplicationService<Student> implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "jsfcrud")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentsService() {
        super(Student.class);
    }
}
