package com.example.jsfcrud.activepersistence.relation;

import com.example.jsfcrud.activepersistence.Relation;
import static java.lang.String.format;
import static java.lang.String.join;

public interface QueryMethods<T> { 
    
    public void setFields(String constructor);    
    
    public void setOffset(int value);
    
    public void setJoins(String value);       
    
    public void setGroup(String group);
    
    public void setLimit(int value);
    
    public void setOrder(String order);
    
    public void setParams(Object[] params);
    
    public void setWhere(String where);

    public Class<T> getEntityClass();     
    
    public default Relation<T> all() {
        return (Relation<T>) this;
    }
    
    public default Relation<T> limit(int value) {
        setLimit(value); return (Relation<T>) this;
    }

    public default Relation<T> order(String order) {
        setOrder(order); return (Relation<T>) this;
    }

    public default Relation<T> where(String conditions, Object... params) {
        setWhere(conditions); setParams(params); return (Relation<T>) this;
    }    

    public default Relation<T> offset(int value) {
        setOffset(value); return (Relation<T>) this;
    }

    public default Relation<T> select(String... fields) {
        setFields(constructor(fields)); return (Relation<T>) this;
    }

    public default Relation<T> joins(String joins) {
        setJoins(joins); return (Relation<T>) this;
    }

    public default Relation<T> group(String... fields) {
        setGroup(commaSeparated(fields)); return (Relation<T>) this;
    }
    
    private String constructor(String[] fields) {
        return format("new %s(%s)", entityName(), commaSeparated(fields));
    }

    private String commaSeparated(String[] values) {
        return join(", ", values);
    }      
    
    private String entityName() {
        return getEntityClass().getName();
    }

}
