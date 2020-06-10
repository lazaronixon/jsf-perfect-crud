package com.example.jsfcrud.activeservice.relation;

import com.example.jsfcrud.activeservice.Relation;
import static java.lang.String.format;
import static java.lang.String.join;

public interface QueryMethods<T> { 
    
    public void setFields(String constructor);    
    
    public void setOffset(int value);
    
    public void setJoins(String value);       
    
    public void setGroup(String group);       
    
    public Relation<T> where(String conditions, Object... params);
    
    public Relation<T> order(String args);
    
    public Relation<T> limit(int value);

    public Class<T> getEntityClass();     
    
    public default Relation<T> all() {
        return (Relation<T>) this;
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
        return format("new %s(%s)", getEntityClass().getName(), commaSeparated(fields));
    }

    private String commaSeparated(String[] values) {
        return join(", ", values);
    }      

}
