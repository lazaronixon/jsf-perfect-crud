package com.example.jsfcrud.controllers;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public abstract class ApplicationController {

    protected String redirectTo(String path) {
        if (path.endsWith(".xhtml")) {
            return path + "?faces-redirect=true";
        } else {
            return path + "&faces-redirect=true";
        }
    }

    protected String redirectTo(String path, Severity severity, String message) {
        getFacesContext().addMessage(null, new FacesMessage(severity, message, null));
        getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
        return redirectTo(path);
    }

    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected Map<String, String> getParams() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }

}
