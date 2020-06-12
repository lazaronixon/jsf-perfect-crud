package com.example.jsfcrud.activepersistence.relation;

import java.util.List;

public interface Calculation<T> {                     
    
    public List fetchAlt();    
    
    public <R> R fetchOneAs(Class<R> resultClass);      
    
    public void setSelectString(String select);      
    
    public void setCalculation(boolean value);

    public default long count() {
        return count("this");
    }
    
    public default long count(String field) {        
        setSelectString("COUNT(" + field + ")"); setCalculation(true); return this.fetchOneAs(Long.class);
    }

    public default <R> R minimum(String field, Class<R> resultClass) {
        setSelectString("MIN(" + field + ")"); setCalculation(true); return this.fetchOneAs(resultClass);
    }

    public default <R> R maximum(String field, Class<R> resultClass) {
        setSelectString("MAX(" + field + ")"); setCalculation(true); return this.fetchOneAs(resultClass);
    }

    public default <R> R average(String field, Class<R> resultClass) {
        setSelectString("AVG(" + field + ")"); setCalculation(true); return this.fetchOneAs(resultClass);
    }

    public default <R> R sum(String field, Class<R> resultClass) {
        setSelectString("SUM(" + field + ")"); setCalculation(true); return this.fetchOneAs(resultClass);
    }

    public default List pluck(String fields) {
        setSelectString(fields); return this.fetchAlt();
    }

    public default List ids() {
        return pluck("this.id");
    }   
    
}
