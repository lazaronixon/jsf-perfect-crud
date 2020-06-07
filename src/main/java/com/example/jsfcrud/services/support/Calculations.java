package com.example.jsfcrud.services.support;

import static java.lang.String.format;

public interface Calculations<T> extends EntityManager<T> {

    public final static String SELECT_COUNT = "SELECT COUNT(%s) FROM %s";
    public final static String SELECT_COUNT_WHERE = "SELECT COUNT(%s) FROM %s WHERE %s";
    public final static String SELECT_CALCULATION = "SELECT %s FROM %s";
    public final static String SELECT_CALCULATION_WHERE = "SELECT %s FROM %s this WHERE %s";

    public default long count(String field) {
        return createQuery(format(SELECT_COUNT, field, entityName()), Long.class).getSingleResult();
    }

    public default long count(String field, String where, Object... params) {
        return createQuery(format(SELECT_COUNT_WHERE, field, entityName(), where), Long.class).getSingleResult();
    }

    public default <R> R calc(String expr, Class<R> resultClass) {
        return createQuery(format(SELECT_CALCULATION, expr, entityName()), resultClass).getSingleResult();
    }

    public default <R> R calc(String expr, Class<R> resultClass, String where, Object... params) {
        return parametize(createQuery(format(SELECT_CALCULATION_WHERE, expr, entityName(), where), resultClass), params).getSingleResult();
    }

}
