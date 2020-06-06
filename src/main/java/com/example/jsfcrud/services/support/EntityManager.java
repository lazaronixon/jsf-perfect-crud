package com.example.jsfcrud.services.support;

import static java.util.stream.IntStream.range;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


public interface EntityManager<T> {

    public javax.persistence.EntityManager getEntityManager();

    public Class<T> getEntityClass();

    public default TypedQuery<T> createQuery(String qlString) {
        return getEntityManager().createQuery(qlString, getEntityClass());
    }

    public default <R> TypedQuery<R> createQuery(String qlString, Class<R> resultClass) {
        return getEntityManager().createQuery(qlString, resultClass);
    }

    public default String entityName() {
        return getEntityClass().getSimpleName();
    }

    public default <R> TypedQuery<R> parametize(TypedQuery<R> query, Object[] parameters) {
        range(0, parameters.length).forEach(i -> query.setParameter(i + 1, parameters[i])); return query;
    }

    public default Query parametize(Query query, Object[] parameters) {
        range(0, parameters.length).forEach(i -> query.setParameter(i + 1, parameters[i])); return query;
    }

}
