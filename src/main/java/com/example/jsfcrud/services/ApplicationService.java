package com.example.jsfcrud.services;

import com.example.jsfcrud.services.support.Base;

public abstract class ApplicationService<T> implements Base<T> {

    private final Class<T> entityClass;

    public ApplicationService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

}
