package com.example.jsfcrud.helpers;

import com.example.jsfcrud.models.Student;

public class StudentsHelper {

    public static String editStudentPath(Student student) {
        return "/views/students/edit.xhtml?id=" + student.getId();
    }

    public static String studentsPath() {
        return "/views/students/index.xhtml";
    }

    public static String newStudentPath() {
        return "/views/students/new.xhtml";
    }

    public static String studentPath(Student student) {
        return "/views/students/show.xhtml?id=" + student.getId();
    }

}
