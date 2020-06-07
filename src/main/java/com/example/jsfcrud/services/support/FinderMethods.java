package com.example.jsfcrud.services.support;

import static java.lang.String.format;

public interface FinderMethods<T> extends EntityManager<T> {

    public static final String SELECT_EXISTS = "SELECT 1 AS one FROM %s this %s";

    public T find(String id);

    public default T find(Object id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    public default T findBy(String query, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_QUERY, getEntityClass(), query)), params).setMaxResults(1).getResultStream().findFirst().orElse(null);
    }

    public default T findByAlt(String query, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_QUERY, getEntityClass(), query)), params).setMaxResults(1).getSingleResult();
    }

    public default boolean exists(String query, Object... params) {
        return parametize(createQuery(format(SELECT_EXISTS, entityName(), query)), params).setMaxResults(1).getResultStream().findFirst().isPresent();
    }

}
