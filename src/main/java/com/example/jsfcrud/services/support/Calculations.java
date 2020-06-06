package com.example.jsfcrud.services.support;

import static java.lang.String.format;

public interface Calculations<T> extends EntityManager<T>  {

    public final static String SELECT_COUNT_STAR = "SELECT COUNT(this) FROM %s this";
    public final static String SELECT_COUNT_STAR_WHERE = "SELECT COUNT(this) FROM %s this WHERE %s";
    public final static String SELECT_CALCULATION = "SELECT %s(%s) FROM %s this";
    public final static String SELECT_CALCULATION_WHERE = "SELECT %s(%s) FROM %s this WHERE %s";

    public default Long count() {
        return createQuery(format(SELECT_COUNT_STAR, entityName()), Long.class).getSingleResult();
    }

    public default Long count(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_COUNT_STAR_WHERE, entityName(), condition), Long.class), params).getSingleResult();
    }

    public default Long count(String field) {
        return createQuery(format(SELECT_CALCULATION, "COUNT", field, entityName()), Long.class).getSingleResult();
    }

    public default Long count(String field, String condition, Object... params) {
        return parametize(createQuery(format(SELECT_CALCULATION_WHERE, "COUNT", field, entityName(), condition), Long.class), params).getSingleResult();
    }

    public default <R> R average(String field, Class<R> resultClass) {
        return createQuery(format(SELECT_CALCULATION, "AVG", field, entityName()), resultClass).getSingleResult();
    }

    public default <R> R average(String field, Class<R> resultClass, String condition, Object... params) {
        return parametize(createQuery(format(SELECT_CALCULATION_WHERE, "AVG", field, entityName(), condition), resultClass), params).getSingleResult();
    }

    public default <R> R minimum(String field, Class<R> resultClass) {
        return createQuery(format(SELECT_CALCULATION, "MIN", field, entityName()), resultClass).getSingleResult();
    }

    public default <R> R minimum(String field, Class<R> resultClass, String condition, Object... params) {
        return parametize(createQuery(format(SELECT_CALCULATION_WHERE, "MIN", field, entityName(), condition), resultClass), params).getSingleResult();
    }

    public default <R> R maximum(String field, Class<R> resultClass) {
        return createQuery(format(SELECT_CALCULATION, "MAX", field, entityName()), resultClass).getSingleResult();
    }

    public default <R> R maximum(String field, Class<R> resultClass, String condition, Object... params) {
        return parametize(createQuery(format(SELECT_CALCULATION_WHERE, "MAX", field, entityName(), condition), resultClass), params).getSingleResult();
    }

    public default <R> R sum(String field, Class<R> resultClass) {
        return createQuery(format(SELECT_CALCULATION, "SUM", field, entityName()), resultClass).getSingleResult();
    }

    public default <R> R sum(String field, Class<R> resultClass, String condition, Object... params) {
        return parametize(createQuery(format(SELECT_CALCULATION_WHERE, "SUM", field, entityName(), condition), resultClass), params).getSingleResult();
    }

}
