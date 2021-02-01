package com.example.jsfcrud.repositories;

import com.example.jsfcrud.models.Student;
import static java.lang.Long.parseLong;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class StudentRepository extends ApplicationRepository<Student> {

    public StudentRepository() {
        super(Student.class);
    }

    public Student find(String id) {
        return super.find(parseLong(id));
    }

}
