package com.example.jsfcrud.services.support;

import com.example.jsfcrud.models.ApplicationRecord;
import javax.transaction.Transactional;

public interface Persistence<T> extends EntityManager<T> {

    @Transactional
    public default T create(T entity) {
        getEntityManager().persist(entity); return entity;
    }

    @Transactional
    public default T update(T entity) {
        return getEntityManager().merge(entity);
    }

    @Transactional
    public default void destroy(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Transactional
    public default T save(ApplicationRecord entity) {
        if (entity.isNewRecord()) {
            return create((T) entity);
        } else {
            return update((T) entity);
        }
    }

    public default void reload(T entity) {
        getEntityManager().refresh(entity);
    }

}
