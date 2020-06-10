package com.example.jsfcrud.activeservice.relation;

import static java.lang.String.join;
import java.util.List;

public interface Calculation<T> {
    
    public void setFields(String fields);    
    
    public List fetchAlt();
    
    public <R> R fetchOneAs(Class<R> resultClass);         

    public default long count() {
        return count("this");
    }

    public default long count(String field) {
        setFields("COUNT(" + field + ")"); return this.fetchOneAs(Long.class);
    }

    public default <R> R min(String field, Class<R> resultClass) {
        setFields("MIN(" + field + ")"); return this.fetchOneAs(resultClass);
    }

    public default <R> R max(String field, Class<R> resultClass) {
        setFields("MAX(" + field + ")"); return this.fetchOneAs(resultClass);
    }

    public default <R> R avg(String field, Class<R> resultClass) {
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