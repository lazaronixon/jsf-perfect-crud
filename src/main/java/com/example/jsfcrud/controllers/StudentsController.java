package com.example.jsfcrud.controllers;

import static com.example.jsfcrud.helpers.StudentsHelper.studentPath;
import static com.example.jsfcrud.helpers.StudentsHelper.studentsPath;
import com.example.jsfcrud.models.Student;
import com.example.jsfcrud.repositories.StudentsRepository;
import java.io.Serializable;
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
    private StudentsRepository studentsRepository;

    private List<Student> students;

    private Student student;

    public void index() {
        students = studentsRepository.all();
    }

    public void new_() {
        student = new Student();
    }

    public String create() {
        studentsRepository.save(student);
        return redirectTo(studentPath(student), SEVERITY_INFO, "Student was successfully created.");
    }

    public String update() {
        studentsRepository.save(student);
        return redirectTo(studentPath(student), SEVERITY_INFO, "Student was successfully updated.");
    }

    public String destroy() {
        studentsRepository.destroy(student);
        return redirectTo(studentsPath(), SEVERITY_INFO, "Student was successfully destroyed.");
    }

    public void loadStudent() {
        student = studentsRepository.find(getParams().get("id"));
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
