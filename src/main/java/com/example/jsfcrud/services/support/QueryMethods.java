package com.example.jsfcrud.services.support;

import java.util.List;
import static java.util.stream.IntStream.range;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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

    public default List<T> findBySql(String sql, Object... params) {
        return parametize(createNativeQuery(sql), params).getResultList();
    }

    public default List selectAll(String sqlQuery, Object... params) {
        return parametize(createNativeQuery(sqlQuery), params).getResultList();
    }

    private Relation<T> buildRelation() {
        return new Relation(getEntityManager(), getEntityClass());
    }

    private Query createNativeQuery(String sqlQuery) {
        return getEntityManager().createNativeQuery(sqlQuery);
    }

    private Query parametize(Query query, Object[] params) {
        range(0, params.length).forEach(i -> query.setParameter(i + 1, params[i])); return query;
    }

}
