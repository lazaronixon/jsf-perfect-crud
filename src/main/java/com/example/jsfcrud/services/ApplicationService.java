package com.example.jsfcrud.services;

import com.example.jsfcrud.models.ApplicationRecord;
import static java.lang.String.format;
import java.util.List;
import static java.util.stream.IntStream.range;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public abstract class ApplicationService<T> {

    private final static String SELECT_STAR = "SELECT this FROM %s this";
    private final static String SELECT_STAR_ORDER = "SELECT this FROM %s this ORDER BY %s";
    private final static String SELECT_STAR_WHERE = "SELECT this FROM %s this WHERE %s";
    private final static String SELECT_STAR_WHERE_ORDER = "SELECT this FROM %s this WHERE %s ORDER BY %s";
    private final static String SELECT_COUNT_STAR = "SELECT COUNT(this) FROM %s this";
    private final static String SELECT_COUNT_STAR_WHERE = "SELECT COUNT(this) FROM %s this WHERE %s";
    private final static String SELECT_AVERAGE = "SELECT AVG(%s) FROM %s this";
    private final static String SELECT_AVERAGE_WHERE = "SELECT AVG(%s) FROM %s this WHERE %s";
    private final static String SELECT_MINIMUM = "SELECT MIN(%s) FROM %s this";
    private final static String SELECT_MINIMUM_WHERE = "SELECT MIN(%s) FROM %s this WHERE %s";
    private final static String SELECT_MAXIMUM = "SELECT MAX(%s) FROM %s this";
    private final static String SELECT_MAXIMUM_WHERE = "SELECT MAX(%s) FROM %s this WHERE %s";
    private final static String SELECT_SUM = "SELECT SUM(%s) FROM %s this";
    private final static String SELECT_SUM_WHERE = "SELECT SUM(%s) FROM %s this WHERE %s";
    private final static String SELECT_1_AS_ONE = "SELECT 1 AS one FROM %s this WHERE %s";

    private final Class<T> entityClass;

    public ApplicationService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public abstract T find(String id);

    public void create(ApplicationRecord entity) {
        getEntityManager().persist(entity);
    }

    public void update(ApplicationRecord entity) {
        getEntityManager().merge(entity);
    }

    public void save(ApplicationRecord entity) {
        if (entity.isNewRecord()) {
            getEntityManager().persist(entity);
        } else {
            getEntityManager().merge(entity);
        }
    }

    public void destroy(ApplicationRecord entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public T findBy(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_STAR, entityName())), params).setMaxResults(1).getResultStream().findFirst().orElse(null);
    }

    public List<T> all() {
        return createQuery(format(SELECT_STAR, entityName())).getResultList();
    }

    public List<T> order(String order) {
        return createQuery(format(SELECT_STAR_ORDER, entityName(), order)).getResultList();
    }

    public List<T> order(String order, int limit) {
        return createQuery(format(SELECT_STAR_ORDER, entityName(), order)).setMaxResults(limit).getResultList();
    }

    public List<T> order(String order, int limit, int offset) {
        return createQuery(format(SELECT_STAR_ORDER, entityName(), order)).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public List<T> where(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE, entityName(), condition)), params).getResultList();
    }

    public List<T> where(String condition, int limit, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE, entityName(), condition)), params).setMaxResults(limit).getResultList();
    }

    public List<T> where(String condition, int limit, int offset, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE, entityName(), condition)), params).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public List<T> where(String condition, String order, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE_ORDER, entityName(), condition, order)), params).getResultList();
    }

    public List<T> where(String condition, String order, int limit, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE_ORDER, entityName(), condition, order)), params).setMaxResults(limit).getResultList();
    }

    public List<T> where(String condition, String order, int limit, int offset, Object... params) {
        return parametize(createQuery(format(SELECT_STAR_WHERE_ORDER, entityName(), condition, order)), params).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public Long count() {
        return createQuery(format(SELECT_COUNT_STAR, entityName()), Long.class).getSingleResult();
    }

    public Long count(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_COUNT_STAR_WHERE, entityName(), condition), Long.class), params).getSingleResult();
    }

    public <R> R average(String field, Class<R> resultClass) {
        return createQuery(format(SELECT_AVERAGE, field, entityName()), resultClass).getSingleResult();
    }

    public <R> R average(String field, Class<R> resultClass, String condition, Object... params) {
        return parametize(createQuery(format(SELECT_AVERAGE_WHERE, field, entityName(), condition), resultClass), params).getSingleResult();
    }

    public <R> R minimum(String field, Class<R> resultClass) {
        return createQuery(format(SELECT_MINIMUM, field, entityName()), resultClass).getSingleResult();
    }

    public <R> R minimum(String field, Class<R> resultClass, String condition, Object... params) {
        return parametize(createQuery(format(SELECT_MINIMUM_WHERE, field, entityName(), condition), resultClass), params).getSingleResult();
    }

    public <R> R maximum(String field, Class<R> resultClass) {
        return createQuery(format(SELECT_MAXIMUM, field, entityName()), resultClass).getSingleResult();
    }

    public <R> R maximum(String field, Class<R> resultClass, String condition, Object... params) {
        return parametize(createQuery(format(SELECT_MAXIMUM_WHERE, field, entityName(), condition), resultClass), params).getSingleResult();
    }

    public <R> R sum(String field, Class<R> resultClass) {
        return createQuery(format(SELECT_SUM, field, entityName()), resultClass).getSingleResult();
    }

    public <R> R sum(String field, Class<R> resultClass, String condition, Object... params) {
        return parametize(createQuery(format(SELECT_SUM_WHERE, field, entityName(), condition), resultClass), params).getSingleResult();
    }

    public boolean exists(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_1_AS_ONE, entityName(), condition)), params).setMaxResults(1).getResultStream().findFirst().isPresent();
    }

    protected TypedQuery<T> createQuery(String qlString) {
        return getEntityManager().createQuery(qlString, entityClass);
    }

    protected TypedQuery<T> createNamedQuery(String name) {
        return getEntityManager().createNamedQuery(name, entityClass);
    }

    protected <R> TypedQuery<R> createQuery(String qlString, Class<R> resultClass) {
        return getEntityManager().createQuery(qlString, resultClass);
    }

    protected Query createNativeQuery(String sqlString, Class resultClass) {
        return getEntityManager().createNativeQuery(sqlString, resultClass);
    }

    private <R> TypedQuery<R> parametize(TypedQuery<R> query, Object[] parameters) {
        range(0, parameters.length).forEach(i -> query.setParameter(i + 1, parameters[i])); return query;
    }

    private String entityName() {
        return entityClass.getSimpleName();
    }

}
