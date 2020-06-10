package com.example.jsfcrud.activepersistence.relation;

import static java.lang.String.join;
import java.util.List;

public interface Calculation<T> {
    
    public void setFields(String fields);        
    
    public <R> R fetchOneAs(Class<R> resultClass);         
    
    public List fetchAlt();    

    public default long count() {
        return count("this");
    }

    public default long count(String field) {
        setFields("COUNT(" + field + ")"); return this.fetchOneAs(Long.class);
    }

    public default <R> R minimum(String field, Class<R> resultClass) {
        setFields("MIN(" + field + ")"); return this.fetchOneAs(resultClass);
    }

    public default <R> R maximum(String field, Class<R> resultClass) {
        setFields("MAX(" + field + ")"); return this.fetchOneAs(resultClass);
    }

    public default <R> R average(String field, Class<R> resultClass) {
        setFields("AVG(" + field + ")"); return this.fetchOneAs(resultClass);
    }

    public default <R> R sum(String field, Class<R> resultClass) {
        setFields("SUM(" + field + ")"); return this.fetchOneAs(resultClass);
    }

    public default List pluck(String... fields) {
        setFields(commaSeparated(fields)); return this.fetchAlt();
    }     

    public default List ids() {
        return pluck("this.id");
    }  
    
    private String commaSeparated(String[] values) {
        return join(", ", values);
    }  
    
}
