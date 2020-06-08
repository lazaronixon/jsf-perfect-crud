package com.example.jsfcrud.services;

import com.example.jsfcrud.services.support.Base;
import com.example.jsfcrud.services.support.Relation;

public abstract class ApplicationService<T> extends Base<T> {

    public abstract T find(String id);

    private final Class<T> entityClass;

    public ApplicationService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public Relation<T> buildRelation() {
        return new Relation(this);
    }

}
