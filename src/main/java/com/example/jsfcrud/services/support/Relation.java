package com.example.jsfcrud.services.support;

import com.example.jsfcrud.services.ApplicationService;
import static java.lang.String.format;
import static java.lang.String.join;
import java.util.List;
import static java.util.Optional.ofNullable;
import static java.util.stream.IntStream.range;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Relation<T> {

    private final static String SELECT_FRAGMENT = "SELECT %s FROM %s this";
    private final static String WHERE_FRAGMENT  = "WHERE %s";
    private final static String GROUP_FRAGMENT  = "GROUP BY %s";
    private final static String ORDER_FRAGMENT  = "ORDER BY %s";

    private final EntityManager entityManager;

    private final Class entityClass;

    private Object[] params = new Object[0];

    private String fields = "this";

    private String where;

    private String group;

    private String order;

    private String joins;

    private int limit;

    private int offset;

    public Relation(ApplicationService service) {
        this.entityManager = service.getEntityManager();
        this.entityClass = service.getEntityClass();
    }

    public T take() {
        return limit(1).fetchSingle();
    }

    public T takeAlt() {
        return limit(1).fetchSingleAlt();
    }

    public T first() {
        return order(firstOrder()).take();
    }

    public T firstAlt() {
        return order(firstOrder()).takeAlt();
    }

    public T last() {
        return order(lastOrder()).take();
    }

    public T lastAlt() {
        return order(lastOrder()).takeAlt();
    }

    public T findBy(String conditions, Object... params) {
        return where(conditions, params).take();
    }

    public T findByAlt(String conditions, Object... params) {
        return where(conditions, params).takeAlt();
    }

    public boolean exists() {
        return limit(1).fetchExists();
    }

    public List<T> take(int limit) {
        return limit(limit).fetch();
    }

    public List<T> first(int limit) {
        return order(firstOrder()).take(limit);
    }

    public List<T> last(int limit) {
        return order(lastOrder()).take(limit);
    }

    public Relation<T> all() {
        return this;
    }

    public Relation<T> where(String conditions, Object... params) {
        this.where = conditions; this.params = params; return this;
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

    public Relation<T> joins(String joins) {
        this.joins = joins; return this;
    }

    public Relation<T> group(String... fields) {
        this.group = comma_separated(fields); return this;
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

    public List pluck(String... fields) {
        this.fields = comma_separated(fields); return this.fetchGeneric();
    }

    public List ids() {
        return pluck("this.id");
    }

    public List<T> fetch() {
        return createParameterizedQuery(buildQlString()).getResultList();
    }

    private String buildQlString() {
        StringBuilder qlString = new StringBuilder(formattedSelect());
        if (joins != null) qlString.append(" ").append(joins);
        if (where != null) qlString.append(" ").append(formattedWhere());
        if (group != null) qlString.append(" ").append(formattedGroup());
        if (order != null) qlString.append(" ").append(formattedOrder());
        return qlString.toString();
    }

    private String formattedSelect() {
        return format(SELECT_FRAGMENT, fields, entityClass.getSimpleName());
    }

    private String formattedWhere() {
        return format(WHERE_FRAGMENT, where);
    }

    private String formattedGroup() {
        return format(GROUP_FRAGMENT, group);
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

    private List fetchGeneric() {
        return createParameterizedQueryGeneric(buildQlString()).getResultList();
    }

    private TypedQuery<T> createParameterizedQuery(String qlString) {
        return parametize(createQuery(qlString)).setMaxResults(limit).setFirstResult(offset);
    }

    private Query createParameterizedQueryGeneric(String qlString) {
        return parametize(createQueryGeneric(qlString)).setMaxResults(limit).setFirstResult(offset);
    }

    private <R> TypedQuery<R> createParameterizedQuery(String qlString, Class<R> resultClass) {
        return parametize(createQuery(qlString, resultClass)).setMaxResults(limit).setFirstResult(offset);
    }

    private TypedQuery<T> createQuery(String qlString) {
        return entityManager.createQuery(qlString, entityClass);
    }

    private <R> TypedQuery<R> createQuery(String qlString, Class<R> resultClass) {
        return entityManager.createQuery(qlString, resultClass);
    }

    private Query createQueryGeneric(String qlString) {
        return entityManager.createQuery(qlString);
    }

    private String firstOrder() {
        return ofNullable(this.order).orElse("this.id");
    }

    private String lastOrder() {
        return ofNullable(this.order).orElse("this.id DESC");
    }

    private String constructor(String[] fields) {
        return format("new %s(%s)", entityClass.getName(), comma_separated(fields));
    }

    private String comma_separated(String[] values) {
        return join(", ", values);
    }

    private <R> TypedQuery<R> parametize(TypedQuery<R> query) {
        range(0, params.length).forEach(i -> query.setParameter(i + 1, params[i])); return query;
    }

    private Query parametize(Query query) {
        range(0, params.length).forEach(i -> query.setParameter(i + 1, params[i])); return query;
    }
}
