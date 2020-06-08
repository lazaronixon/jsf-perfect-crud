
package com.example.jsfcrud.services.support;

import javax.persistence.EntityManager;

public interface FinderMethods<T> {

    public EntityManager getEntityManager();

    public Class<T> getEntityClass();

    public default T find(Object id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    public default T take() {
        return buildRelation().take();
    }

    public default T takeAlt() {
        return buildRelation().takeAlt();
    }

    public default T findBy(String conditions, Object... params) {
        return buildRelation().findBy(conditions, params);
    }

    public default T findByAlt(String conditions, Object... params) {
        return buildRelation().findByAlt(conditions, params);
    }

    public default boolean exists(String conditions, Object... params) {
        return buildRelation().exists(conditions, params);
    }

    private Relation<T> buildRelation() {
        return new Relation(getEntityManager(), getEntityClass());
    }

}
