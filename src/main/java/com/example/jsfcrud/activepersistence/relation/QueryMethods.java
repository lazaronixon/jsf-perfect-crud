package com.example.jsfcrud.activepersistence.relation;

import com.example.jsfcrud.activepersistence.Relation;
import static java.lang.String.format;

public interface QueryMethods<T> { 
    
    public void addSelect(String select);    
    
    public void addJoins(String joins);     
    
    public void addWhere(String where);

    public void addParams(Object[] params);   
    
    public void addGroup(String group);  
    
    public void addHaving(String having);
    
    public void addOrder(String order);    
    
    public void setOffset(int offset);             
    
    public void setLimit(int limit);   
    
    public void setDistinct(boolean value);
    
    public boolean isDistinct();    
    
    public void addIncludes(String[] includes);
    
    public void addEagerLoads(String[] eagerLoads);
    
    public void clearSelect();
    
    public void clearWhere();
    
    public void clearOrder();

    public Class<T> getEntityClass();     
    
    public default Relation<T> all() {
        return (Relation<T>) this;
    }
    
    public default Relation<T> select(String values) {        
        addSelect(distinctExp() + constructor(values)); return (Relation<T>) this;
    }    
    
    public default Relation<T> joins(String values) {
        addJoins(values); return (Relation<T>) this;
    }    
    
    public default Relation<T> where(String conditions, Object... params) {
        addWhere(conditions); addParams(params); return (Relation<T>) this;
    }
    
    public default Relation<T> group(String values) {
        addGroup(values); return (Relation<T>) this;
    }    
    
    public default Relation<T> having(String conditions, Object... params) {
        addHaving(conditions); addParams(params); return (Relation<T>) this;
    }     
    
    public default Relation<T> order(String order) {
        addOrder(order); return (Relation<T>) this;
    }     
    
    public default Relation<T> limit(int limit) {
        setLimit(limit); return (Relation<T>) this;
    }
    
    public default Relation<T> offset(int offset) {
        setOffset(offset); return (Relation<T>) this;
    }
    
    public default Relation<T> distinct() {
        setDistinct(true); return (Relation<T>) this;
    }
    
    public default Relation<T> distinct(boolean value) {
        setDistinct(value); return (Relation<T>) this;
    }    
    
    public default Relation<T> none() {
        addWhere("1 = 0"); return (Relation<T>) this;
    }
    
    public default Relation<T> includes(String... includes) {
        addIncludes(includes); return (Relation<T>) this; 
    }
    
    public default Relation<T> eagerLoads(String... eagerLoads) {
        addEagerLoads(eagerLoads); return (Relation<T>) this; 
    }
    
    public default Relation<T> reselect(String values) {
        clearSelect(); return select(values);
    }
    
    public default Relation<T> rewhere(String conditions, Object... params) {
        clearWhere(); return where(conditions, params);
    }   
    
    public default Relation<T> reorder(String fields) {
        clearOrder(); return order(fields);
    }     
    
    private String constructor(String values) {
        return format("new %s(%s)", entityName(), values);
    }    
    
    private String distinctExp() {
        return isDistinct() ? "DISTINCT " : "";
    }    
    
    private String entityName() {
        return getEntityClass().getName();
    }

}
