package com.example.jsfcrud.activepersistence.relation;

import com.example.jsfcrud.activepersistence.Relation;
import static java.lang.String.format;

public interface QueryMethods<T> { 
    
    public void setSelect(String select);    
    
    public void setJoins(String joins);     
    
    public void setWhere(String where);

    public void setWhereParams(Object[] params);   
    
    public void setGroup(String group);  
    
    public void setHaving(String having);     
    
    public void setHavingParams(Object[] params);         
    
    public void setOrder(String order);    
    
    public void setOffset(int offset);             
    
    public void setLimit(int limit);   
    
    public void setDistinct(boolean value);
    
    public boolean isDistinct();    

    public Class<T> getEntityClass();     
    
    public default Relation<T> all() {
        return (Relation<T>) this;
    }
    
    public default Relation<T> select(String values) {        
        setSelect(distinctMod() + constructor(values)); return (Relation<T>) this;
    }    
    
    public default Relation<T> joins(String values) {
        setJoins(values); return (Relation<T>) this;
    }    
    
    public default Relation<T> where(String conditions, Object... params) {
        setWhere(conditions); setWhereParams(params); return (Relation<T>) this;
    }     
    
    public default Relation<T> group(String values) {
        setGroup(values); return (Relation<T>) this;
    }    
    
    public default Relation<T> having(String conditions, Object... params) {
        setHaving(conditions); setHavingParams(params); return (Relation<T>) this;
    }       
    
    public default Relation<T> order(String order) {
        setOrder(order); return (Relation<T>) this;
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
    
    private String constructor(String values) {
        return format("new %s(%s)", entityName(), values);
    }    
    
    private String distinctMod() {
        return isDistinct() ? "DISTINCT " : "";
    }    
    
    private String entityName() {
        return getEntityClass().getName();
    }

}
