package com.example.jsfcrud.services.support;

import com.example.jsfcrud.services.ApplicationService;
import static java.lang.String.format;
import static java.lang.String.join;
import java.util.List;
import static java.util.stream.IntStream.range;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class Relation<T> {

    private final static String SELECT_FRAGMENT = "SELECT %s FROM %s this";
    private final static String WHERE_FRAGMENT  = "WHERE %s";
    private final static String ORDER_FRAGMENT  = "ORDER BY %s";

    private final EntityManager entityManager;

    private final Class entityClass;

    private String fields = "this";

    private Object[] params = new Object[0];

    private String conditions;

    private String order;

    private int limit;

    private int offset;

    public Relation(ApplicationService service) {
        this.entityManager = service.getEntityManager();
        this.entityClass = service.getEntityClass();
    }

    public T findBy(String conditions, Object... params) {
        return where(conditions, params).limit(1).fetchSingle();
    }

    public T findByAlt(String conditions, Object... params) {
        return where(conditions, params).limit(1).fetchSingleAlt();
    }

    public boolean exists(String conditions, Object... params) {
        return where(conditions, params).limit(1).fetchExists();
    }

    public Relation<T> all() {
        return this;
    }

    public Relation<T> where(String conditions, Object... params) {
        this.conditions = conditions; this.params = params; return this;
    }

    public Relation<T> order(String args) {
        this.order = args; return this;
    }

    public Relation<T> limit(int value) {
        this.limit = value; return this;
    }

    public Relation<T> offset(int value) {
        this.offset = value; return this;
    }

    public Relation<T> select(String... fields) {
        this.fields = constructor(fields); return this;
    }

    public long count() {
        return count("this");
    }

    public long count(String field) {
        this.fields = "COUNT(" + field + ")"; return this.fetchSingleAs(Long.class);
    }

    public <R> R min(String field, Class<R> resultClass) {
        this.fields = "MIN(" + field + ")"; return this.fetchSingleAs(resultClass);
    }

    public <R> R max(String field, Class<R> resultClass) {
        this.fields = "MAX(" + field + ")"; return this.fetchSingleAs(resultClass);
    }

    public <R> R avg(String field, Class<R> resultClass) {
        this.fields = "AVG(" + field + ")"; return this.fetchSingleAs(resultClass);
    }

    public <R> R sum(String field, Class<R> resultClass) {
        this.fields = "SUM(" + field + ")"; return this.fetchSingleAs(resultClass);
    }

    public List<T> fetch() {
        return createParameterizedQuery(buildQlString()).getResultList();
    }

    private String buildQlString() {
        StringBuilder qlString = new StringBuilder(formattedSelect());
        if (conditions != null) qlString.append(" ").append(formattedWhere());
        if (order != null) qlString.append(" ").append(formattedOrder());
        return qlString.toString();
    }

    private String formattedSelect() {
        return format(SELECT_FRAGMENT, fields, entityClass.getSimpleName());
    }

    private String formattedWhere() {
        return format(WHERE_FRAGMENT, conditions);
    }

    private String formattedOrder() {
        return format(ORDER_FRAGMENT, order);
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

    private String constructor(String... fields) {
        return format("new %s(%s)", entityClass.getName(), join(", ", fields));
    }

    private <R> TypedQuery<R> parametize(TypedQuery<R> query, Object[] params) {
        range(0, params.length).forEach(i -> query.setParameter(i + 1, params[i])); return query;
    }
}
