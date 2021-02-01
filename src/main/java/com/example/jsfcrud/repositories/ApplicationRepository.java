package com.example.jsfcrud.repositories;

import com.example.jsfcrud.models.ApplicationRecord;
import static java.lang.String.format;
import java.util.List;
import java.util.function.Supplier;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public abstract class ApplicationRepository<T> {

    @PersistenceContext
    private EntityManager em;

    private final Class<T> entityClass;

    public ApplicationRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public T save(ApplicationRecord entity) {
        if (entity.isDestroyed()) return (T) entity;

        if (entity.isNewRecord()) {
            return create((T) entity);
        } else {
            return update((T) entity);
        }
    }

    @Transactional
    public void destroy(ApplicationRecord entity) {
        if (!entity.isPersisted()) return;

        flush(() -> {
            if (em.contains(entity)) {
                em.remove(entity);
            } else {
                em.remove(em.merge(entity));
            }
        });
    }

    public void reload(T entity) {
        em.refresh(entity);
    }

    public T find(Object id) {
        return em.find(entityClass, id);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public List<T> all() {
        return em.createQuery(format("SELECT this FROM %s this", getName())).getResultList();
    }

    public List<T> order(String fields) {
        return em.createQuery(format("SELECT this FROM %s this ORDER BY %s", getName(), fields)).getResultList();
    }

    public String where(String conditions) {
        return format("SELECT this FROM %s this WHERE ", getName()) + conditions;
    }

    private T create(T entity) {
        return flush(() -> { em.persist(entity); return entity; });
    }

    private T update(T entity) {
        return flush(() -> em.merge(entity));
    }

    private T flush(Supplier<T> yield) {
        var result = yield.get(); em.flush(); return result;
    }

    private void flush(Runnable yield) {
        yield.run(); em.flush();
    }

    private String getName() {
        return entityClass.getSimpleName();
    }

}
