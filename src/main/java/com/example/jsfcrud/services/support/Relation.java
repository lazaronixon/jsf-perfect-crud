package com.example.jsfcrud.services.support;

import com.example.jsfcrud.services.ApplicationService;
import static java.lang.String.format;
import java.util.List;
import static java.util.stream.IntStream.range;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class Relation<T> {

    private final static String SELECT_FRAGMENT = "SELECT %s FROM %s this";
    private final static String WHERE_FRAGMENT = "WHERE %s";
    private final static String ORDER_FRAGMENT = "ORDER BY %s";

    private final ApplicationService service;

    private String fields = "this";

    private Object[] params = new Object[0];

    private String conditions;

    private String order;

    private int limit;

    private int offset;

    public Relation(ApplicationService service) {
        this.service = service;
    }

    public T take() {
        this.limit = 1;
        return this.fetchSingle();
    }

    public T takeAlt() {
        this.limit = 1;
        return this.fetchSingleAlt();
    }

    public T findBy(String conditions, Object... params) {
        return where(conditions, params).take();
    }

    public T findByAlt(String conditions, Object... params) {
        return where(conditions, params).takeAlt();
    }

    public boolean exists(String conditions, Object... params) {
        return where(conditions, params).fetchExists();
    }

    public Relation<T> all() {
        return this;
    }

    public Relation<T> where(String conditions, Object... params) {
        this.conditions = conditions;
        this.params = params;
        return this;
    }

    public Relation<T> order(String order) {
        this.order = order;
        return this;
    }

    public Relation<T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Relation<T> offset(int offset) {
        this.offset = offset;
        return this;
    }

    public Relation<T> select(String fields) {
        this.fields = constructor(fields);
        return this;
    }

    public long count() {
        return count("this");
    }

    public long count(String field) {
        this.fields = "COUNT(" + field + ")";
        return fetchSingleAs(Long.class);
    }

    public <R> R min(String field, Class<R> resultClass) {
        this.fields = "MIN(" + field + ")";
        return fetchSingleAs(resultClass);
    }

    public <R> R max(String field, Class<R> resultClass) {
        this.fields = "MAX(" + field + ")";
        return fetchSingleAs(resultClass);
    }

    public <R> R avg(String field, Class<R> resultClass) {
        this.fields = "AVG(" + field + ")";
        return fetchSingleAs(resultClass);
    }

    public <R> R sum(String field, Class<R> resultClass) {
        this.fields = "SUM(" + field + ")";
        return fetchSingleAs(resultClass);
    }

    public List<T> fetch() {
        return createParameterizedQuery(buildQlString()).getResultList();
    }

    private String buildQlString() {
        StringBuilder qlString = new StringBuilder(format(SELECT_FRAGMENT, fields, entitySimpleName()));
        if (conditions != null) qlString.append(" ").append(format(WHERE_FRAGMENT, conditions));
        if (order != null) qlString.append(" ").append(format(ORDER_FRAGMENT, order));
        return qlString.toString();
    }

    private T fetchSingle() {
        return createParameterizedQuery(buildQlString()).getResultStream().findFirst().orElse(null);
    }

    private T fetchSingleAlt() {
        return createParameterizedQuery(buildQlString()).getSingleResult();
    }

    private <R> R fetchSingleAs(Class<R> resultClass) {
        return createParameterizedQuery(buildQlString(), resultClass).getSingleResult();
    }

    private boolean fetchExists() {
        return createParameterizedQuery(buildQlString()).getResultStream().findAny().isPresent();
    }

    private TypedQuery<T> createParameterizedQuery(String qlString) {
        return parametize(createQuery(qlString), params).setMaxResults(limit).setFirstResult(offset);
    }

    private <R> TypedQuery<R> createParameterizedQuery(String qlString, Class<R> resultClass) {
        return parametize(createQuery(qlString, resultClass), params).setMaxResults(limit).setFirstResult(offset);
    }

    private TypedQuery<T> createQuery(String qlString) {
        return entityManager().createQuery(qlString, entityClass());
    }

    private <R> TypedQuery<R> createQuery(String qlString, Class<R> resultClass) {
        return entityManager().createQuery(qlString, resultClass);
    }

    private String constructor(String fields) {
        return format("new %s(%s)", entityName(), fields);
    }

    private String entitySimpleName() {
        return entityClass().getSimpleName();
    }

    private String entityName() {
        return entityClass().getName();
    }

    private EntityManager entityManager() {
        return service.getEntityManager();
    }

    private Class entityClass() {
        return service.getEntityClass();
    }

    private <R> TypedQuery<R> parametize(TypedQuery<R> query, Object[] params) {
        range(0, params.length).forEach(i -> query.setParameter(i + 1, params[i])); return query;
    }
}
