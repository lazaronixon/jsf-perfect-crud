package com.example.jsfcrud.services.support;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public interface QueryMethods<T> {

    public EntityManager getEntityManager();

    public Class<T> getEntityClass();

    public Relation<T> buildRelation();

    public default Relation<T> all() {
        return buildRelation().all();
    }

    public default Relation<T> where(String conditions, Object... params) {
        return buildRelation().where(conditions, params);
    }

    public default Relation<T> order(String arg) {
        return buildRelation().order(arg);
    }

    public default Relation<T> limit(int value) {
        return buildRelation().limit(value);
    }

    public default Relation<T> offset(int value) {
        return buildRelation().offset(value);
    }

    public default Relation<T> select(String... fields) {
        return buildRelation().select(fields);
    }

    public default Relation<T> joins(String joins) {
        return buildRelation().joins(joins);
    }

    public default Query createNativeQuery(String qlString) {
        return getEntityManager().createNativeQuery(qlString, getEntityClass());
    }

    public default Query createNativeQuery(String qlString, Class resultClass) {
        return getEntityManager().createNativeQuery(qlString, resultClass);
    }

    public default TypedQuery<T> createQuery(String qlString) {
        return getEntityManager().createQuery(qlString, getEntityClass());
    }

    public default <R> TypedQuery<R> createQuery(String qlString, Class<R> resultClass) {
        return getEntityManager().createQuery(qlString, resultClass);
    }

    public default Query createNativeQueryGeneric(String sqlQuery) {
        return getEntityManager().createNativeQuery(sqlQuery);
    }

}
