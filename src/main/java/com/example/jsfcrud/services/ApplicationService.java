package com.example.jsfcrud.services;

import com.example.jsfcrud.services.support.Base;
import javax.persistence.EntityManager;

public abstract class ApplicationService<T> extends Base<T> {

    private final Class<T> entityClass;

    public ApplicationService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract T find(String id);

    @Override
    public abstract EntityManager getEntityManager();

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

}
