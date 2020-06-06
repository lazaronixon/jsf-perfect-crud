package com.example.jsfcrud.services.support;

import com.example.jsfcrud.models.ApplicationRecord;
import javax.transaction.Transactional;

public interface Persistence<T> extends EntityManager<T> {

    @Transactional
    public default void create(ApplicationRecord entity) {
        getEntityManager().persist(entity);
    }

    @Transactional
    public default void update(ApplicationRecord entity) {
        getEntityManager().merge(entity);
    }

    @Transactional
    public default void destroy(ApplicationRecord entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Transactional
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
