package com.example.jsfcrud.controllers;

import java.util.Map;
import java.util.concurrent.Callable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class ApplicationController {

    protected String redirectTo(String path) {
        if (path == null) return null;

        if (path.endsWith(".xhtml")) {
            return path + "?faces-redirect=true";
        } else {
            return path + "&faces-redirect=true";
        }
    }

    protected String redirectTo(String path, String notice) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, notice, null));
        getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
        return redirectTo(path);
    }
    
    protected String redirectTo(String path, Runnable action, String notice) {
        action.run();
        return redirectTo(path, notice);
    }

    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected Map<String, String> getParams() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }

}
