package com.example.jsfcrud.services;

import com.example.jsfcrud.models.Student;
import static java.lang.Long.parseLong;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentsService extends ApplicationService<Student> {

    public StudentsService() {
        super(Student.class);
    }

    public Student find(String id) {
        return super.find(parseLong(id));
    }

}
