package com.example.jsfcrud.activeservice.relation;

import com.example.jsfcrud.activeservice.Relation;
import java.util.List;
import javax.persistence.EntityManager;

public interface Delegation<T> {

    public EntityManager getEntityManager();

    public Class<T> getEntityClass();

    public Relation<T> buildRelation();

    public default long count() {
        return buildRelation().count();
    }

    public default long count(String field) {
        return buildRelation().count(field);
    }

    public default <R> R min(String field, Class<R> resultClass) {
       return buildRelation().min(field, resultClass);
    }

    public default <R> R max(String field, Class<R> resultClass) {
        return buildRelation().max(field, resultClass);
    }

    public default <R> R avg(String field, Class<R> resultClass) {
        return buildRelation().avg(field, resultClass);
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

    public default T find(Object id) {
        return getEntityManager().find(getEntityClass(), id);
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

}