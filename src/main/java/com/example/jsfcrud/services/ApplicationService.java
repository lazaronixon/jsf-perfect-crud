package com.example.jsfcrud.services;

import com.example.jsfcrud.models.ApplicationRecord;
import static java.lang.String.format;
import java.util.List;
import static java.util.stream.IntStream.range;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class ApplicationService<T> {

    private final static String SELECT_STAR = "SELECT this FROM %s this";
    private final static String SELECT_STAR_ORDER = "SELECT this FROM %s this ORDER BY %s";
    private final static String SELECT_STAR_WHERE = "SELECT this FROM %s this WHERE %s";
    private final static String SELECT_STAR_WHERE_ORDER = "SELECT this FROM %s this WHERE %s ORDER BY %s";
    private final static String SELECT_COUNT_STAR = "SELECT COUNT(this) FROM %s this";
    private final static String SELECT_COUNT_STAR_WHERE = "SELECT COUNT(this) FROM %s this WHERE %s";

    private final Class<T> entityClass;

    public ApplicationService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract EntityManager getEntityManager();

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

    public List<T> all() {
        return getEntityManager().createQuery(format(SELECT_STAR, entityClass.getName())).getResultList();
    }

    public List<T> all(String order) {
        return getEntityManager().createQuery(format(SELECT_STAR_ORDER, entityClass.getName(), order)).getResultList();
    }

    public List<T> all(int limit, int offset) {
        return getEntityManager().createQuery(format(SELECT_STAR, entityClass.getSimpleName())).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public List<T> all(int limit, int offset, String order) {
        return getEntityManager().createQuery(format(SELECT_STAR_ORDER, entityClass.getSimpleName(), order)).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    public List<T> where(String conditions, Object... params) {
        Query query = getEntityManager().createQuery(format(SELECT_STAR_WHERE, entityClass.getName(), conditions));
        setPositionalParameters(query, params);
        return query.getResultList();
    }

    public List<T> where(String conditions, String order, Object... params) {
        Query query = getEntityManager().createQuery(format(SELECT_STAR_WHERE_ORDER, entityClass.getName(), conditions, order));
        setPositionalParameters(query, params);
        return query.getResultList();
    }

    public List<T> where(String conditions, int limit, int offset, Object... params) {
        Query query = getEntityManager().createQuery(format(SELECT_STAR_WHERE, entityClass.getName(), conditions)).setMaxResults(limit).setFirstResult(offset);
        setPositionalParameters(query, params);
        return query.getResultList();
    }

    public List<T> where(String conditions, int limit, int offset, String order, Object... params) {
        Query query = getEntityManager().createQuery(format(SELECT_STAR_WHERE_ORDER, entityClass.getName(), conditions, order)).setMaxResults(limit).setFirstResult(offset);
        setPositionalParameters(query, params);
        return query.getResultList();
    }

    public Long count() {
        return getEntityManager().createQuery(format(SELECT_COUNT_STAR, entityClass.getSimpleName()), Long.class).getSingleResult();
    }

    public Long count(String conditions) {
        return getEntityManager().createQuery(format(SELECT_COUNT_STAR_WHERE, entityClass.getSimpleName(), conditions), Long.class).getSingleResult();
    }

    private void setPositionalParameters(Query query, Object[] positionalParameters) {
        range(0, positionalParameters.length).forEach(i -> query.setParameter(i, positionalParameters[i]));
    }

}
