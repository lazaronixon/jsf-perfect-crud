
package com.example.jsfcrud.services.support;

import javax.persistence.EntityManager;

public interface Calculations<T> {

    public EntityManager getEntityManager();

    public Class<T> getEntityClass();

    public default long count() {
        return buildRelation().count();
    }

    public default long count(String field) {
        return buildRelation().count(field);
    }

    public default <R> R min(String field, Class<R> resultClass) {
       return buildRelation().min(field, resultClass);
    }

    public default <R> R max(String field, Class<R> resultClass) {
        return buildRelation().max(field, resultClass);
    }

    public default <R> R avg(String field, Class<R> resultClass) {
        return buildRelation().avg(field, resultClass);
    }

    public default <R> R sum(String field, Class<R> resultClass) {
        return buildRelation().sum(field, resultClass);
    }

    private Relation<T> buildRelation() {
        return new Relation(getEntityManager(), getEntityClass());
    }

}
