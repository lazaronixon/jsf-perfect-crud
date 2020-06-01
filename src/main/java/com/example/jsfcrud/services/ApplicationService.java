package com.example.jsfcrud.services;

import com.example.jsfcrud.models.ApplicationRecord;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
        CriteriaQuery cq = createCriteriaQuery(entityClass);
        Root star = cq.from(entityClass);
        return getEntityManager().createQuery(cq.select(star)).getResultList();
    }
    
    private CriteriaQuery<T> createCriteriaQuery(Class resultClass) {
        return getEntityManager().getCriteriaBuilder().createQuery(resultClass);
    }
           
}
