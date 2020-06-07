package com.example.jsfcrud.services.support;

import static java.lang.String.format;
import java.util.List;

public interface QueryMethods<T> extends EntityManager<T> {

    public default List<T> all() {
        return createQuery(format(SELECT_STAR, entityName())).getResultList();
    }

    public default List<T> all(String query, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_QUERY, entityName(), query)), params).getResultList();
    }

    public default List<T> allLimit(int limit) {
        return createQuery(format(SELECT_STAR, entityName())).setMaxResults(limit).getResultList();
    }

    public default List<T> allLimit(int limit, String query, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_QUERY, entityName(), query)), params).setMaxResults(limit).getResultList();
    }

    public default List<T> allLimitOffset(int limit, int offset) {
        return createQuery(format(SELECT_STAR, entityName())).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public default List<T> allLimitOffset(int limit, int offset, String query, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_QUERY, entityName(), query)), params).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public default List<T> select(String qlString, Object... params) {
        return parametize(getEntityManager().createQuery(qlString, getEntityClass()), params).getResultList();
    }

    public default List<T> selectLimit(int limit, String qlString, Object... params) {
        return parametize(getEntityManager().createQuery(qlString, getEntityClass()), params).setMaxResults(limit).getResultList();
    }

    public default List<T> selectLimitOffset(int limit, int offset, String qlString, Object... params) {
        return parametize(getEntityManager().createQuery(qlString, getEntityClass()), params).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public default List<T> findBySql(String sql, Object... params) {
        return parametize(getEntityManager().createNativeQuery(sql, getEntityClass()), params).getResultList();
    }

    public default List<T> findBySqlLimit(int limit, String sql, Object... params) {
        return parametize(getEntityManager().createNativeQuery(sql, getEntityClass()), params).setMaxResults(limit).getResultList();
    }

    public default List<T> findBySqlLimitOffset(int limit, int offset, String sql, Object... params) {
        return parametize(getEntityManager().createNativeQuery(sql, getEntityClass()), params).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public default List<Object[]> selectAll(String sql, Object... params) {
        return parametize(getEntityManager().createNativeQuery(sql), params).getResultList();
    }

    public default List<Object[]> selectAllLimit(int limit, String sql, Object... params) {
        return parametize(getEntityManager().createNativeQuery(sql), params).setMaxResults(limit).getResultList();
    }

    public default List<Object[]> selectAllLimitOffset(int limit, int offset, String sql, Object... params) {
        return parametize(getEntityManager().createNativeQuery(sql), params).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

}
