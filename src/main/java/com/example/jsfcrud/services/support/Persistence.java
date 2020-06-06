package com.example.jsfcrud.services.support;

import com.example.jsfcrud.models.ApplicationRecord;

public interface Persistence<T> extends EntityManager<T> {

    public default void create(ApplicationRecord entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    public default void update(ApplicationRecord entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public default void destroy(ApplicationRecord entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }

    public default void save(ApplicationRecord entity) {
        if (entity.isNewRecord()) {
            create(entity);
        } else {
            update(entity);
        }
    }

    public default void reload(ApplicationRecord entity) {
        getEntityManager().refresh(entity);
    }

}
