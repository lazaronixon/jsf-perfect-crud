package com.example.jsfcrud.activepersistence;

import com.example.jsfcrud.services.ApplicationService;
import java.util.List;
import javax.persistence.EntityManager;

public abstract class Base<T> implements Persistence<T>, Querying<T>  {

    @Override
    public abstract EntityManager getEntityManager();

    @Override
    public abstract Class<T> getEntityClass();
    
    public long count() {
        return buildRelation().count();
    }

    public long count(String field) {
        return buildRelation().count(field);
    }

    public <R> R minimum(String field, Class<R> resultClass) {
       return buildRelation().minimum(field, resultClass);
    }

    public <R> R maximum(String field, Class<R> resultClass) {
        return buildRelation().maximum(field, resultClass);
    }

    public <R> R average(String field, Class<R> resultClass) {
        return buildRelation().average(field, resultClass);
    }

    public <R> R sum(String field, Class<R> resultClass) {
        return buildRelation().sum(field, resultClass);
    }

    public List pluck(String field) {
        return buildRelation().pluck(field);
    }

    public List ids() {
        return buildRelation().ids();
    }

    public T take() {
        return buildRelation().take();
    }

    public T takeAlt() {
        return buildRelation().takeAlt();
    }

    public T first() {
        return buildRelation().first();
    }

    public T firstAlt() {
        return buildRelation().firstAlt();
    }

    public T last() {
        return buildRelation().last();
    }

    public T lastAlt() {
        return buildRelation().lastAlt();
    }

    public List<T> take(int limit) {
        return buildRelation().take(limit);
    }

    public List<T> first(int limit) {
        return buildRelation().first(limit);
    }

    public List<T> last(int limit) {
        return buildRelation().last(limit);
    }

    public T findBy(String conditions, Object... params) {
        return buildRelation().findBy(conditions, params);
    }

    public T findByAlt(String conditions, Object... params) {
        return buildRelation().findByAlt(conditions, params);
    }

    public boolean exists() {
        return buildRelation().exists();
    }
    
    public boolean exists(String conditions, Object... params) {
        return buildRelation().exists(conditions, params);
    }  

    public Relation<T> all() {
        return buildRelation().all();
    }

    public Relation<T> where(String conditions, Object... params) {
        return buildRelation().where(conditions, params);
    }

    public Relation<T> order(String values) {
        return buildRelation().order(values);
    }

    public Relation<T> limit(int value) {
        return buildRelation().limit(value);
    }

    public Relation<T> offset(int value) {
        return buildRelation().offset(value);
    }

    public Relation<T> select(String values) {
        return buildRelation().select(values);
    }

    public Relation<T> joins(String values) {
        return buildRelation().joins(values);
    }

    public Relation<T> group(String values) {
        return buildRelation().group(values);
    }
    
    public Relation<T> having(String conditions, Object... params) {
        return buildRelation().having(conditions, params);
    }
    
    public Relation<T> distinct() {
        return buildRelation().distinct();
    }
    
    public Relation<T> distinct(boolean value) {
        return buildRelation().distinct(value);
    }    
    
    public Relation<T> none() {
        return buildRelation().none();
    }       
    
    public Relation<T> includes(String... values) {
        return buildRelation().includes(values);
    }
    
    public Relation<T> eagerLoads(String... values) {
        return buildRelation().eagerLoads(values);
    }
    
    private Relation<T> buildRelation() {
        return new Relation((ApplicationService) this);
    }    

}
