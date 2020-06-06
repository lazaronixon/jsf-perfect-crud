package com.example.jsfcrud.services.support;

import com.example.jsfcrud.models.ApplicationRecord;

public interface Persistence<T> extends EntityManager<T> {

    public default void create(ApplicationRecord entity) {
        getEntityManager().persist(entity);
    }

    public default void update(ApplicationRecord entity) {
        getEntityManager().merge(entity);
    }

    public default void destroy(ApplicationRecord entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public default void save(ApplicationRecord entity) {
        if (entity.isNewRecord()) {
            getEntityManager().persist(entity);
        } else {
            getEntityManager().merge(entity);
        }
    }

    public default void reload(ApplicationRecord entity) {
        getEntityManager().refresh(entity);
    }

}
