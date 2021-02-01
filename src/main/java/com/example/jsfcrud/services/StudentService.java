package com.example.jsfcrud.services;

import com.example.jsfcrud.models.Student;
import static java.lang.Long.parseLong;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class StudentService extends ApplicationService<Student> {

    public StudentService() {
        super(Student.class);
    }

    public Student find(String id) {
        return super.find(parseLong(id));
    }

}
