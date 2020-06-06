package com.example.jsfcrud.services;

import com.example.jsfcrud.models.ApplicationRecord;
import static java.lang.String.format;
import java.util.List;
import static java.util.stream.IntStream.range;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public abstract class ApplicationService<T> {

    private final static String SELECT_ALL = "SELECT this FROM %s this";
    private final static String SELECT_ALL_ORDER = "SELECT this FROM %s this ORDER BY %s";
    private final static String SELECT_ALL_WHERE = "SELECT this FROM %s this WHERE %s";
    private final static String SELECT_ALL_WHERE_ORDER = "SELECT this FROM %s this WHERE %s ORDER BY %s";
    private final static String SELECT_COUNT_ALL = "SELECT COUNT(this) FROM %s this";
    private final static String SELECT_COUNT_ALL_WHERE = "SELECT COUNT(this) FROM %s this WHERE %s";
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
        return parametize(createQuery(format(SELECT_ALL, entityName())), params).setMaxResults(1).getResultStream().findFirst().orElse(null);
    }

    public List<T> all() {
        return createQuery(format(SELECT_ALL, entityName())).getResultList();
    }

    public List<T> order(String order) {
        return createQuery(format(SELECT_ALL_ORDER, entityName(), order)).getResultList();
    }

    public List<T> order(String order, int limit) {
        return createQuery(format(SELECT_ALL_ORDER, entityName(), order)).setMaxResults(limit).getResultList();
    }

    public List<T> order(String order, int limit, int offset) {
        return createQuery(format(SELECT_ALL_ORDER, entityName(), order)).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public List<T> where(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_ALL_WHERE, entityName(), condition)), params).getResultList();
    }

    public List<T> whereLimit(String condition, int limit, Object... params) {
        return parametize(createQuery(format(SELECT_ALL_WHERE, entityName(), condition)), params).setMaxResults(limit).getResultList();
    }

    public List<T> whereInRange(String condition, int limit, int offset, Object... params) {
        return parametize(createQuery(format(SELECT_ALL_WHERE, entityName(), condition)), params).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public List<T> whereOrder(String condition, String order, Object... params) {
        return parametize(createQuery(format(SELECT_ALL_WHERE_ORDER, entityName(), condition, order)), params).getResultList();
    }

    public List<T> whereOrderLimit(String condition, String order, int limit, Object... params) {
        return parametize(createQuery(format(SELECT_ALL_WHERE_ORDER, entityName(), condition, order)), params).setMaxResults(limit).getResultList();
    }

    public List<T> whereOrderInRange(String condition, String order, int limit, int offset, Object... params) {
        return parametize(createQuery(format(SELECT_ALL_WHERE_ORDER, entityName(), condition, order)), params).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public Long count() {
        return createQuery(format(SELECT_COUNT_ALL, entityName()), Long.class).getSingleResult();
    }

    public Long count(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_COUNT_ALL_WHERE, entityName(), condition), Long.class), params).getSingleResult();
    }

    public boolean exists(String condition, Object... params) {
        return parametize(createQuery(format(SELECT_1_AS_ONE, entityName(), condition)), params).setMaxResults(1).getResultStream().findFirst().isPresent();
    }

    protected TypedQuery<T> createQuery(String qlString) {
        return getEntityManager().createQuery(qlString, entityClass);
    }

    protected <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
        return getEntityManager().createQuery(qlString, resultClass);
    }

    private <T> TypedQuery<T> parametize(TypedQuery<T> query, Object[] parameters) {
        range(0, parameters.length).forEach(i -> query.setParameter(i + 1, parameters[i]));
        return query;
    }

    private String entityName() {
        return entityClass.getSimpleName();
    }

}
