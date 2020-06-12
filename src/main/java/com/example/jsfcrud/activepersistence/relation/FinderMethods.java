package com.example.jsfcrud.activepersistence.relation;

import com.example.jsfcrud.activepersistence.Relation;
import static java.lang.String.join;
import java.util.List;

public class FinderMethods<T> {               
    
    private final Relation<T> relation;

    public FinderMethods(Relation relation) {
        this.relation = relation;
    }        
    
    public T take() {
        return relation.limit(1).fetchOne();
    }

    public T takeAlt() {
        return relation.limit(1).fetchOneAlt();
    }

    public T first() {
        return relation.order(firstOrder()).take();
    }

    public T firstAlt() {
        return relation.order(firstOrder()).takeAlt();
    }

    public T last() {
        return relation.order(lastOrder()).take();
    }

    public T lastAlt() {
        return relation.order(lastOrder()).takeAlt();
    }

    public T findBy(String conditions, Object... params) {
        return relation.where(conditions, params).take();
    }

    public T findByAlt(String conditions, Object... params) {
        return relation.where(conditions, params).takeAlt();
    }
    
    public boolean exists(String conditions, Object... params) {
        return relation.where(conditions, params).exists();
    }

    public boolean exists() {
        return relation.limit(1).fetchExists();
    }

    public List<T> take(int limit) {
        return relation.limit(limit).fetch();
    }

    public List<T> first(int limit) {
        return relation.order(firstOrder()).take(limit);
    }

    public List<T> last(int limit) {
        return relation.order(lastOrder()).take(limit);
    }    
    
    private String firstOrder() {
        return relation.getOrderValues().isEmpty() ? "this.id" : separatedByComma(relation.getOrderValues());
    }

    private String lastOrder() {
        return relation.getOrderValues().isEmpty() ? "this.id DESC" : separatedByComma(relation.getOrderValues());
    }
    
    private String separatedByComma(List<String> values) {
        return join(", ", values);
    }
    
}
