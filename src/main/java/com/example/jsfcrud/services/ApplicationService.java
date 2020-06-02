package com.example.jsfcrud.services;

import com.example.jsfcrud.models.ApplicationRecord;
import static java.lang.String.format;
import java.util.List;
import javax.persistence.EntityManager;

public abstract class ApplicationService<T> {

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
        return getEntityManager().createQuery(format("SELECT this FROM %s this", entityClass.getName())).getResultList();
    }
    
    public List<T> all(String order) {
        return getEntityManager().createQuery(format("SELECT this FROM %s this ORDER BY %s", entityClass.getName(), order)).getResultList();
    }
    
    public List<T> allInRange(int first, int pageSize) {
        return getEntityManager().createQuery(format("SELECT this FROM %s this", entityClass.getSimpleName())).setFirstResult(first).setMaxResults(pageSize).getResultList();
    }
    
    public Long count() {
        return getEntityManager().createQuery(format("SELECT COUNT(this) FROM %s this", entityClass.getSimpleName()), Long.class).getSingleResult();
    }    

}
