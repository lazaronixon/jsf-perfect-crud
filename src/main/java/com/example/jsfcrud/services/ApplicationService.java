package com.example.jsfcrud.services;

import com.example.jsfcrud.models.ApplicationRecord;
import static java.lang.String.format;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

public abstract class ApplicationService<T> {

    protected abstract EntityManager getEntityManager();

    public abstract T find(String id);

    private final Class<T> entityClass;

    public ApplicationService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public T create(T entity) {
        getEntityManager().persist(entity); return entity;
    }

    @Transactional
    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    @Transactional
    public void destroy(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Transactional
    public T save(ApplicationRecord entity) {
        if (entity.isNewRecord()) {
            return create((T) entity);
        } else {
            return update((T) entity);
        }
    }

    public void reload(T entity) {
        getEntityManager().refresh(entity);
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> all() {
        return buildQuery(format("SELECT this FROM %s this", entityClass.getSimpleName())).getResultList();
    }

    public Query buildNativeQuery(String qlString) {
        return getEntityManager().createNativeQuery(qlString, entityClass);
    }

    public Query buildNativeQuery(String qlString, Class resultClass) {
        return getEntityManager().createNativeQuery(qlString, resultClass);
    }

    public Query buildNativeQueryAlt(String sqlQuery) {
        return getEntityManager().createNativeQuery(sqlQuery);
    }

    public TypedQuery<T> buildQuery(String qlString) {
        return getEntityManager().createQuery(qlString, entityClass);
    }

    public <R> TypedQuery<R> buildQuery(String qlString, Class<R> resultClass) {
        return getEntityManager().createQuery(qlString, resultClass);
    }

}
