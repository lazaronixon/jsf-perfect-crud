package com.example.jsfcrud.activepersistence.relation;

import com.example.jsfcrud.activepersistence.Relation;
import java.util.List;

public class Calculation<T> {    
    
    private final Relation<T> relation;

    public Calculation(Relation relation) {
        this.relation = relation;
    }        

    public long count() {
        return count("this");
    }
    
    public long count(String field) {        
        relation.setSelectString("COUNT(" + field + ")"); relation.setCalculating(true); return relation.fetchOneAs(Long.class);
    }

    public <R> R minimum(String field, Class<R> resultClass) {
        relation.setSelectString("MIN(" + field + ")"); relation.setCalculating(true); return relation.fetchOneAs(resultClass);
    }

    public <R> R maximum(String field, Class<R> resultClass) {
        relation.setSelectString("MAX(" + field + ")"); relation.setCalculating(true); return relation.fetchOneAs(resultClass);
    }

    public <R> R average(String field, Class<R> resultClass) {
        relation.setSelectString("AVG(" + field + ")"); relation.setCalculating(true); return relation.fetchOneAs(resultClass);
    }

    public <R> R sum(String field, Class<R> resultClass) {
        relation.setSelectString("SUM(" + field + ")"); relation.setCalculating(true); return relation.fetchOneAs(resultClass);
    }

    public List pluck(String fields) {
        relation.setSelectString(fields); return relation.fetchAlt();
    }

    public List ids() {
        return pluck("this.id");
    }   
    
}
