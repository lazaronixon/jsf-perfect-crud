package com.example.jsfcrud.controllers;

import com.example.jsfcrud.models.Student;
import com.example.jsfcrud.services.StudentService;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class StudentsController extends ApplicationController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private StudentService studentService;

    private List<Student> students;

    private Student student;

    public void index() {
        students = studentService.all();
    }

    public void build() {
        student = new Student();
    }

    public String create() {
        studentService.create(student);
        return redirectTo("/views/students/show.xhtml?id=" + student.getId(), "Student was successfully created.");
    }

    public String update() {
        studentService.update(student);
        return redirectTo("/views/students/show.xhtml?id=" + student.getId(), "Student was successfully updated.");
    }

    public String destroy() {
        studentService.destroy(student);
        return redirectTo("/views/students/index.xhtml", "Student was successfully destroyed.");
    }

    public void loadStudent() {
        student = studentService.find(Integer.parseInt(getParams().get("id")));
    }

    //<editor-fold defaultstate="collapsed" desc="Get/Set">
    public List<Student> getStudents() {
        return students;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    //</editor-fold>
}
