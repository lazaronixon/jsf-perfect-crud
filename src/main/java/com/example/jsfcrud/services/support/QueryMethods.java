package com.example.jsfcrud.services.support;

import static com.example.jsfcrud.services.support.FinderMethods.SELECT_STAR;
import static java.lang.String.format;
import java.util.List;

public interface QueryMethods<T> extends EntityManager<T> {

    public final static String SELECT_STAR_ORDER = "SELECT this FROM %s this ORDER BY %s";
    public final static String SELECT_STAR_WHERE = "SELECT this FROM %s this WHERE %s";
    public final static String SELECT_STAR_WHERE_ORDER = "SELECT this FROM %s this WHERE %s ORDER BY %s";

    public default List<T> all() {
        return createQuery(format(SELECT_STAR, entityName())).getResultList();
    }

    public default List<T> order(String order) {
        return createQuery(format(SELECT_STAR_ORDER, entityName(), order)).getResultList();
    }

    public default List<T> order(String order, int limit) {
        return createQuery(format(SELECT_STAR_ORDER, entityName(), order)).setMaxResults(limit).getResultList();
    }

    public default List<T> order(String order, int limit, int offset) {
        return createQuery(format(SELECT_STAR_ORDER, entityName(), order)).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public default List<T> where(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE, entityName(), condition)), params).getResultList();
    }

    public default List<T> where(String condition, int limit, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE, entityName(), condition)), params).setMaxResults(limit).getResultList();
    }

    public default List<T> where(String condition, int limit, int offset, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE, entityName(), condition)), params).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public default List<T> where(String condition, String order, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE_ORDER, entityName(), condition, order)), params).getResultList();
    }

    public default List<T> where(String condition, String order, int limit, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE_ORDER, entityName(), condition, order)), params).setMaxResults(limit).getResultList();
    }

    public default List<T> where(String condition, String order, int limit, int offset, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE_ORDER, entityName(), condition, order)), params).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public default List<T> select(String qlString) {
        return getEntityManager().createQuery(qlString, getEntityClass()).getResultList();
    }

    public default List<T> select(String qlString, Object... params) {
        return parametize(getEntityManager().createQuery(qlString, getEntityClass()), params).getResultList();
    }

    public default List<Object[]> findBySql(String sql) {
        return getEntityManager().createNativeQuery(sql).getResultList();
    }

    public default List<Object[]> findBySql(String sql, Object... params) {
        return parametize(getEntityManager().createNativeQuery(sql), params).getResultList();
    }

}
