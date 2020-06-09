package com.example.jsfcrud.services.support;

import java.util.List;
import javax.persistence.EntityManager;

public interface FinderMethods<T> {

    public EntityManager getEntityManager();

    public Class<T> getEntityClass();

    public Relation<T> buildRelation();

    public default T find(Object id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    public default T take() {
        return buildRelation().take();
    }

    public default T takeAlt() {
        return buildRelation().takeAlt();
    }

    public default T first() {
        return buildRelation().first();
    }

    public default T firstAlt() {
        return buildRelation().firstAlt();
    }

    public default T last() {
        return buildRelation().last();
    }

    public default T lastAlt() {
        return buildRelation().lastAlt();
    }

    public default List<T> take(int limit) {
        return buildRelation().take(limit);
    }

    public default List<T> first(int limit) {
        return buildRelation().first(limit);
    }

    public default List<T> last(int limit) {
        return buildRelation().last(limit);
    }

    public default T findBy(String conditions, Object... params) {
        return buildRelation().findBy(conditions, params);
    }

    public default T findByAlt(String conditions, Object... params) {
        return buildRelation().findByAlt(conditions, params);
    }

    public default boolean exists() {
        return buildRelation().exists();
    }

    public default boolean exists(String conditions, Object... params) {
        return buildRelation().exists(conditions, params);
    }

}
