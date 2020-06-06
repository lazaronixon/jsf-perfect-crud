package com.example.jsfcrud.services.support;

import static java.lang.String.format;

public interface FinderMethods<T> extends EntityManager<T> {

    public static final String SELECT_STAR = "SELECT this FROM %s this";
    public static final String SELECT_1_AS_ONE = "SELECT 1 AS one FROM %s this WHERE %s";

    public abstract T find(String id);

    default public T find(Object id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    default public T findBy(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_STAR, getEntityClass())), params).setMaxResults(1).getResultStream().findFirst().orElse(null);
    }

    default public T findBy_(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_STAR, getEntityClass())), params).setMaxResults(1).getSingleResult();
    }

    //take
    //take!

    //first
    //first!

    //last
    //last!


    default public boolean exists(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_1_AS_ONE, entityName(), condition)), params).setMaxResults(1).getResultStream().findFirst().isPresent();
    }

}
