package com.example.jsfcrud.activepersistence;

import com.example.jsfcrud.activepersistence.relation.Delegation;
import com.example.jsfcrud.services.ApplicationService;
import javax.persistence.EntityManager;

public abstract class Base<T> implements Persistence<T>, Querying<T>, Delegation<T>  {

    @Override
    public abstract EntityManager getEntityManager();

    @Override
    public abstract Class<T> getEntityClass();    
    
    @Override
    public Relation<T> buildRelation() {
        return new Relation((ApplicationService) this);
    }

}
