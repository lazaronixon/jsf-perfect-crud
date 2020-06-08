package com.example.jsfcrud.services.support;

import javax.persistence.EntityManager;

public abstract class Base<T> implements Persistence<T>, FinderMethods<T>, QueryMethods<T>, Calculations<T> {

    @Override
    public abstract EntityManager getEntityManager();

}
