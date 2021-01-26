package com.example.jsfcrud.repositories;

import com.example.jsfcrud.models.Student;
import static java.lang.Long.parseLong;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentsRepository extends ApplicationRepository<Student> {

    public StudentsRepository() {
        super(Student.class);
    }

    public Student find(String id) {
        return super.find(parseLong(id));
    }

}
