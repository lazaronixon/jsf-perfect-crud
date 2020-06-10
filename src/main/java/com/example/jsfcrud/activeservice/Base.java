package com.example.jsfcrud.activeservice;

import com.example.jsfcrud.activeservice.relation.Delegation;
import javax.persistence.EntityManager;

public abstract class Base<T> implements Persistence<T>, QueryBuilders<T>, Delegation<T>  {

    @Override
    public abstract EntityManager getEntityManager();

    @Override
    public abstract Class<T> getEntityClass();

}
