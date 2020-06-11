package com.example.jsfcrud.activepersistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public interface Querying<T> {

    public EntityManager getEntityManager();

    public Class<T> getEntityClass();

    public default Query buildNativeQuery(String qlString) {
        return getEntityManager().createNativeQuery(qlString, getEntityClass());
    }

    public default Query buildNativeQuery(String qlString, Class resultClass) {
        return getEntityManager().createNativeQuery(qlString, resultClass);
    }

    public default Query buildNativeQueryAlt(String sqlQuery) {
        return getEntityManager().createNativeQuery(sqlQuery);
    }

    public default TypedQuery<T> buildQuery(String qlString) {
        return getEntityManager().createQuery(qlString, getEntityClass());
    }

    public default <R> TypedQuery<R> buildQuery(String qlString, Class<R> resultClass) {
        return getEntityManager().createQuery(qlString, resultClass);
    }
    
    public default Query buildQueryAlt(String qlString) {
        return getEntityManager().createQuery(qlString);
    }    

}