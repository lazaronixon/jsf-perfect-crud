package com.example.jsfcrud.converters;

import com.example.jsfcrud.models.Student;
import com.example.jsfcrud.repositories.StudentRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "studentsConverter", managed = true)
public class StudentsConverter implements Converter {

    @Inject
    private StudentRepository studentService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value != null ? studentService.find(value) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value != null ? String.valueOf(((Student) value).getId()) : null;
    }

}
