package com.example.jsfcrud.activepersistence.relation;

import static java.lang.String.join;
import java.util.List;

public interface FinderMethods<T> extends QueryMethods<T> {                 
    
    public boolean fetchExists();      
    
    public String buildQlString();       
    
    public List<String> getOrderValues();    
    
    public default T take() {
        return limit(1).fetchOne();
    }

    public default T takeAlt() {
        return limit(1).fetchOneAlt();
    }

    public default T first() {
        return order(firstOrder()).take();
    }

    public default T firstAlt() {
        return order(firstOrder()).takeAlt();
    }

    public default T last() {
        return order(lastOrder()).take();
    }

    public default T lastAlt() {
        return order(lastOrder()).takeAlt();
    }

    public default T findBy(String conditions, Object... params) {
        return where(conditions, params).take();
    }

    public default T findByAlt(String conditions, Object... params) {
        return where(conditions, params).takeAlt();
    }
    
    public default boolean exists(String conditions, Object... params) {
        return where(conditions, params).exists();
    }

    public default boolean exists() {
        return limit(1).fetchExists();
    }

    public default List<T> take(int limit) {
        return limit(limit).fetch();
    }

    public default List<T> first(int limit) {
        return order(firstOrder()).take(limit);
    }

    public default List<T> last(int limit) {
        return order(lastOrder()).take(limit);
    }    
    
    private String firstOrder() {
        return getOrderValues().isEmpty() ? "this.id" : join(", ", getOrderValues());
    }

    private String lastOrder() {
        return getOrderValues().isEmpty() ? "this.id DESC" : join(", ", getOrderValues());
    }
    
}
