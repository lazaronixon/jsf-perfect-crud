package com.example.jsfcrud.services;

import com.example.jsfcrud.models.Student;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentsService extends ApplicationService<Student, Long> {

    @Override
    public Student find(String id) {
        return find(Long.parseLong(id));
    }

}
