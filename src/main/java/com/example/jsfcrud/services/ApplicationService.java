package com.example.jsfcrud.services;

import com.example.jsfcrud.models.ApplicationRecord;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public abstract class ApplicationService<T> {

    private final Class<T> entityClass;

    public ApplicationService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract EntityManager getEntityManager();
    
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
        return getEntityManager().createQuery(select(from())).getResultList();
    }
    
    private CriteriaQuery select(Selection selection) {
        return criteriaBuilder().createQuery().select(selection);
    }
    
    private Root from() {
        return criteriaBuilder().createQuery().from(entityClass);
    }
    
    private CriteriaBuilder criteriaBuilder() {
        return getEntityManager().getCriteriaBuilder();
    }
    
}
