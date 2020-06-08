package com.example.jsfcrud.services.support;

import static java.lang.String.format;
import java.util.List;
import static java.util.stream.IntStream.range;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class Relation<T> {

    private final static String SELECT_FRAGMENT = "SELECT %s FROM %s this";
    private final static String WHERE_FRAGMENT = "WHERE %s";
    private final static String ORDER_FRAGMENT = "ORDER BY %s";

    private final EntityManager entityManager;

    private final Class<T> entityClass;

    private String fields;

    private String conditions;

    private Object[] params;

    private String order;

    private int limit;

    private int offset;

    public Relation(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.params = new Object[0];
        this.fields = "this";
    }

    public T take() {
        this.limit = 1;
        return this.getSingleResult();
    }

    public T takeAlt() {
        this.limit = 1;
        return this.getSingleResultAlt();
    }

    public T findBy(String conditions, Object... params) {
        return where(conditions, params).take();
    }

    public T findByAlt(String conditions, Object... params) {
        return where(conditions, params).takeAlt();
    }

    public boolean exists(String conditions, Object... params) {
        return where(conditions, params).getExistsResult();
    }

    public Relation<T> all() {
        return this;
    }

    public Relation<T> where(String conditions, Object... params) {
        this.conditions = conditions;
        this.params = params;
        return this;
    }

    public Relation<T> order(String args) {
        this.order = args;
        return this;
    }

    public Relation<T> limit(int value) {
        this.limit = value;
        return this;
    }

    public Relation<T> offset(int value) {
        this.offset = value;
        return this;
    }

    public Relation<T> select(String fields) {
        this.fields = constructor(fields);
        return this;
    }

    public long count() {
        this.fields = "COUNT(this)";
        return this.getSingleResultAs(Long.class);
    }

    public long count(String field) {
        this.fields = "COUNT(" + field + ")";
        return this.getSingleResultAs(Long.class);
    }

    public <R> R min(String field, Class<R> resultClass) {
        this.fields = "MIN(" + field + ")";
        return this.getSingleResultAs(resultClass);
    }

    public <R> R max(String field, Class<R> resultClass) {
        this.fields = "MAX(" + field + ")";
        return this.getSingleResultAs(resultClass);
    }

    public <R> R avg(String field, Class<R> resultClass) {
        this.fields = "AVG(" + field + ")";
        return this.getSingleResultAs(resultClass);
    }

    public <R> R sum(String field, Class<R> resultClass) {
        this.fields = "SUM(" + field + ")";
        return this.getSingleResultAs(resultClass);
    }

    public List<T> list() {
        return createParameterizedQuery(buildQlString()).getResultList();
    }

    private String buildQlString() {
        StringBuilder qlString = new StringBuilder(format(SELECT_FRAGMENT, fields, entitySimpleName()));
        if (conditions != null) qlString.append(" ").append(format(WHERE_FRAGMENT, conditions));
        if (order != null) qlString.append(" ").append(format(ORDER_FRAGMENT, order));
        return qlString.toString();
    }

    private <R> R getSingleResultAs(Class<R> resultClass) {
        return createParameterizedQuery(buildQlString(), resultClass).getResultStream().findFirst().orElse(null);
    }

    private T getSingleResult() {
        return createParameterizedQuery(buildQlString()).getResultStream().findFirst().orElse(null);
    }

    private T getSingleResultAlt() {
        return createParameterizedQuery(buildQlString()).getSingleResult();
    }

    private boolean getExistsResult() {
        return createParameterizedQuery(buildQlString()).getResultStream().findFirst().isPresent();
    }

    private TypedQuery<T> createParameterizedQuery(String qlString) {
        return parametize(createQuery(qlString), params).setMaxResults(limit).setFirstResult(offset);
    }

    private <R> TypedQuery<R> createParameterizedQuery(String qlString, Class<R> resultClass) {
        return parametize(createQuery(qlString, resultClass), params).setMaxResults(limit).setFirstResult(offset);
    }

    private TypedQuery<T> createQuery(String qlString) {
        return entityManager.createQuery(qlString, entityClass);
    }

    private <R> TypedQuery<R> createQuery(String qlString, Class<R> resultClass) {
        return entityManager.createQuery(qlString, resultClass);
    }

    private String constructor(String fields) {
        return format("new %s(%s)", entityName(), fields);
    }

    private String entitySimpleName() {
        return entityClass.getSimpleName();
    }

    private String entityName() {
        return entityClass.getName();
    }

    private <R> TypedQuery<R> parametize(TypedQuery<R> query, Object[] params) {
        range(0, params.length).forEach(i -> query.setParameter(i + 1, params[i])); return query;
    }
}
