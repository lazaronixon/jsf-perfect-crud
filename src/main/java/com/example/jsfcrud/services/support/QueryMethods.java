package com.example.jsfcrud.services.support;

import java.util.List;
import javax.persistence.EntityManager;

public interface QueryMethods<T> {

    public EntityManager getEntityManager();

    public Class<T> getEntityClass();

    public default Relation<T> all() {
        return buildRelation().all();
    }

    public default Relation<T> where(String conditions, Object params) {
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

    public default Relation<T> select(String fields) {
        return buildRelation().select(fields);
    }

    public default List<T> findBySql(String sql) {
        return buildRelation().findBySql(sql);
    }

    private Relation<T> buildRelation() {
        return new Relation(getEntityManager(), getEntityClass());
    }

}
