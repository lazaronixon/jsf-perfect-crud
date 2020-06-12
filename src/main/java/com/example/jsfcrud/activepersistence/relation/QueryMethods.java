package com.example.jsfcrud.activepersistence.relation;

import com.example.jsfcrud.activepersistence.Relation;
import static java.lang.String.format;

public class QueryMethods<T> {
    
    private final Relation<T> relation;

    public QueryMethods(Relation relation) {
        this.relation = relation;
    }     
    
    public Relation<T> all() {
        return relation;
    }
    
    public Relation<T> select(String values) {        
        relation.addSelect(constructor(values)); return relation;
    }    
    
    public Relation<T> joins(String values) {
        relation.addJoins(values); return relation;
    }    
    
    public Relation<T> where(String conditions, Object... params) {
        relation.addWhere(conditions); relation.addParams(params); return relation;
    }
    
    public Relation<T> group(String values) {
        relation.addGroup(values); return relation;
    }    
    
    public Relation<T> having(String conditions, Object... params) {
        relation.addHaving(conditions); relation.addParams(params); return relation;
    }     
    
    public Relation<T> order(String order) {
        relation.addOrder(order); return relation;
    }     
    
    public Relation<T> limit(int limit) {
        relation.setLimit(limit); return relation;
    }
    
    public Relation<T> offset(int offset) {
        relation.setOffset(offset); return relation;
    }
    
    public Relation<T> distinct() {
        relation.setDistinct(true); return relation;
    }
    
    public Relation<T> distinct(boolean value) {
        relation.setDistinct(value); return relation;
    }
    
    public Relation<T> none() {
        relation.addWhere("1 = 0"); return relation;
    }
    
    public Relation<T> includes(String... includes) {
        relation.addIncludes(includes); return relation; 
    }
    
    public Relation<T> eagerLoads(String... eagerLoads) {
        relation.addEagerLoads(eagerLoads); return relation; 
    }
    
    public Relation<T> reselect(String values) {
        relation.clearSelect(); return select(values);
    }
    
    public Relation<T> rewhere(String conditions, Object... params) {
        relation.clearWhere(); return where(conditions, params);
    }   
    
    public Relation<T> reorder(String fields) {
        relation.clearOrder(); return order(fields);
    }     
    
    private String constructor(String values) {
        return format("new %s(%s)", entityName(), values);
    }
    
    private String entityName() {
        return relation.getEntityClass().getName();
    }

}
