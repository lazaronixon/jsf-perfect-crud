package com.example.jsfcrud.controllers;

import com.example.jsfcrud.models.Student;
import com.example.jsfcrud.services.StudentsService;
import java.io.Serializable;
import static java.lang.Integer.parseInt;
import java.util.List;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class StudentsController extends ApplicationController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private StudentsService studentsService;

    private List<Student> students;

    private Student student;

    public void index() {
        students = studentsService.all();
    }

    public void build() {
        student = new Student();
    }

    public String create() {
        studentsService.create(student);
        return redirectTo("/views/students/show.xhtml?id=" + student.getId(), SEVERITY_INFO, "Student was successfully created.");
    }

    public String update() {
        studentsService.update(student);
        return redirectTo("/views/students/show.xhtml?id=" + student.getId(), SEVERITY_INFO, "Student was successfully updated.");
    }

    public String destroy() {
        studentsService.destroy(student);
        return redirectTo(null, this::index, SEVERITY_INFO, "Student was successfully destroyed.");
    }

    public void loadStudent() {
        student = studentsService.find(parseInt(getParams().get("id")));
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
