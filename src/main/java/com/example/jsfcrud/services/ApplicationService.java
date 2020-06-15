package com.example.jsfcrud.services;

import com.activepersistence.service.Base;

public abstract class ApplicationService<T> extends Base<T> {

    public ApplicationService(Class<T> entityClass) {
        super(entityClass);
    }

    public abstract T find(String id);

}
