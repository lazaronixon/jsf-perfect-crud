package com.example.jsfcrud.activepersistence.relation;

import com.example.jsfcrud.activepersistence.Relation;
import com.example.jsfcrud.services.ApplicationService;
import java.util.List;

public interface Delegation<T> {

    public default long count() {
        return buildRelation().count();
    }

    public default long count(String field) {
        return buildRelation().count(field);
    }

    public default <R> R minimum(String field, Class<R> resultClass) {
       return buildRelation().minimum(field, resultClass);
    }

    public default <R> R maximum(String field, Class<R> resultClass) {
        return buildRelation().maximum(field, resultClass);
    }

    public default <R> R average(String field, Class<R> resultClass) {
        return buildRelation().average(field, resultClass);
    }

    public default <R> R sum(String field, Class<R> resultClass) {
        return buildRelation().sum(field, resultClass);
    }

    public default List pluck(String field) {
        return buildRelation().pluck(field);
    }

    public default List ids() {
        return buildRelation().ids();
    }

    public default T take() {
        return buildRelation().take();
    }

    public default T takeAlt() {
        return buildRelation().takeAlt();
    }

    public default T first() {
        return buildRelation().first();
    }

    public default T firstAlt() {
        return buildRelation().firstAlt();
    }

    public default T last() {
        return buildRelation().last();
    }

    public default T lastAlt() {
        return buildRelation().lastAlt();
    }

    public default List<T> take(int limit) {
        return buildRelation().take(limit);
    }

    public default List<T> first(int limit) {
        return buildRelation().first(limit);
    }

    public default List<T> last(int limit) {
        return buildRelation().last(limit);
    }

    public default T findBy(String conditions, Object... params) {
        return buildRelation().findBy(conditions, params);
    }

    public default T findByAlt(String conditions, Object... params) {
        return buildRelation().findByAlt(conditions, params);
    }

    public default boolean exists() {
        return buildRelation().exists();
    }

    public default Relation<T> all() {
        return buildRelation().all();
    }

    public default Relation<T> where(String conditions, Object... params) {
        return buildRelation().where(conditions, params);
    }

    public default Relation<T> order(String arg) {
        return buildRelation().order(arg);
    }

    public default Relation<T> limit(int value) {
        return buildRelation().limit(value);
    }

    public default Relation<T> offset(int value) {
        return buildRelation().offset(value);
    }

    public default Relation<T> select(String... fields) {
        return buildRelation().select(fields);
    }

    public default Relation<T> joins(String joins) {
        return buildRelation().joins(joins);
    }

    public default Relation<T> group(String group) {
        return buildRelation().joins(group);
    }
    
    private Relation<T> buildRelation() {
        return new Relation((ApplicationService) this);
    }

}