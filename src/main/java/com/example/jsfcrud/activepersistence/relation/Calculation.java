package com.example.jsfcrud.activepersistence.relation;

import java.util.List;

public interface Calculation<T> {                     
    
    public List fetchAlt();    
    
    public <R> R fetchOneAs(Class<R> resultClass);      
    
    public void setSelect(String select);      
    
    public boolean isDistinct();

    public default long count() {
        return count("this");
    }
    
    public default long count(String field) {
        setSelect("COUNT(" + distinct() + field + ")"); return this.fetchOneAs(Long.class);
    }

    public default <R> R minimum(String field, Class<R> resultClass) {
        setSelect("MIN(" + field + ")"); return this.fetchOneAs(resultClass);
    }

    public default <R> R maximum(String field, Class<R> resultClass) {
        setSelect("MAX(" + field + ")"); return this.fetchOneAs(resultClass);
    }

    public default <R> R average(String field, Class<R> resultClass) {
        setSelect("AVG(" + field + ")"); return this.fetchOneAs(resultClass);
    }

    public default <R> R sum(String field, Class<R> resultClass) {
        setSelect("SUM(" + field + ")"); return this.fetchOneAs(resultClass);
    }

    public default List pluck(String fields) {
        setSelect(fields); return this.fetchAlt();
    }

    public default List ids() {
        return pluck("this.id");
    }
    
    private String distinct() {
        return isDistinct() ? "DISTINCT " : "";
    }        
    
}
