package com.example.jsfcrud.activepersistence;

import com.example.jsfcrud.activepersistence.relation.Calculation;
import com.example.jsfcrud.activepersistence.relation.FinderMethods;
import com.example.jsfcrud.activepersistence.relation.QueryMethods;
import com.example.jsfcrud.services.ApplicationService;
import static java.lang.String.format;
import static java.lang.String.join;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.IntStream.range;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Relation<T> implements FinderMethods<T>, QueryMethods<T>, Querying<T>, Calculation<T> {
    
    private final static String SELECT_FRAGMENT = "SELECT %s FROM %s this";
    private final static String WHERE_FRAGMENT  = "WHERE %s";
    private final static String GROUP_FRAGMENT  = "GROUP BY %s";
    private final static String HAVING_FRAGMENT = "HAVING %s";
    private final static String ORDER_FRAGMENT  = "ORDER BY %s";    

    private final EntityManager entityManager;

    private final Class entityClass;

    private final List<String> selectValues = Arrays.asList("this");      

    private final List<String> whereValues  = new ArrayList();

    private final List<String> groupValues  = new ArrayList();
    
    private final List<String> havingValues = new ArrayList();

    private final List<String> orderValues  = new ArrayList();

    private final List<String> joinsValues  = new ArrayList();
    
    private final List<Object> params = new ArrayList();    
    
    private final List<String> includesValues = new ArrayList();
    
    private final List<String> eagerLoadsValues = new ArrayList();    

    private int limit  = 0;

    private int offset = 0;
    
    private boolean distinct = false;    

    public Relation(ApplicationService service) {
        this.entityManager = service.getEntityManager();
        this.entityClass = service.getEntityClass();
    }

    public T find(Object id) {
        return getEntityManager().find(getEntityClass(), id);
    }
    
    public T fetchOne() {
        return buildParameterizedQuery(buildQlString()).getResultStream().findFirst().orElse(null);
    }    
    
    public T fetchOneAlt() {
        return buildParameterizedQuery(buildQlString()).getSingleResult();
    }
    
    public List<T> fetch() {
        return buildParameterizedQuery(buildQlString()).getResultList();
    }    
    
    @Override
    public <R> R fetchOneAs(Class<R> resultClass) {
        return buildParameterizedQuery(buildQlString(), resultClass).getSingleResult();
    }    
    
    @Override
    public List fetchAlt() {
        return buildParameterizedQueryAlt(buildQlString()).getResultList();
    }     
    
    @Override
    public boolean fetchExists() {
        return buildParameterizedQuery(buildQlString()).getResultStream().findAny().isPresent();
    }

    @Override
    public String buildQlString() {
        StringBuilder qlString = new StringBuilder(formattedSelect());
        if (!joinsValues.isEmpty())  qlString.append(" ").append(separatedBySpace(joinsValues));
        if (!whereValues.isEmpty())  qlString.append(" ").append(formattedWhere());
        if (!groupValues.isEmpty())  qlString.append(" ").append(formattedGroup());
        if (!havingValues.isEmpty()) qlString.append(" ").append(formattedHaving());
        if (!orderValues.isEmpty())  qlString.append(" ").append(formattedOrder());
        return qlString.toString();
    }
    
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }    
    
    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }    
    
    @Override
    public void addSelect(String select) {
        this.selectValues.add(select);
    }
    
    @Override
    public void setSelectString(String select) {
        this.selectValues.clear();
        this.addSelect(select);
    }
    
    @Override
    public void addJoins(String joins) {
        this.joinsValues.add(joins);
    }
    
    @Override
    public void addWhere(String where) {
        this.whereValues.add(where);
    }
    
    @Override
    public void addParams(Object[] params) {
        this.params.add(params);
    }      
    
    @Override
    public void addGroup(String group) {
        this.groupValues.add(group);
    }
    
    @Override
    public void addHaving(String having) {
        this.havingValues.add(having);
    }
    
    @Override
    public List<String> getOrderValues() {
        return orderValues;
    }
    
    @Override
    public void addOrder(String order) {
        this.orderValues.add(order);
    }    
    
    @Override
    public void addIncludes(String[] includes) {
        includesValues.addAll(List.of(includes));
    }

    @Override
    public void addEagerLoads(String[] eagerLoads) {
        eagerLoadsValues.addAll(List.of(eagerLoads));
    }    
    
    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }    

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }    
    
    @Override
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }    
    
    @Override
    public boolean isDistinct() {
        return distinct;
    }
    
    @Override
    public void clearSelect() {
        this.selectValues.clear();
    }

    @Override
    public void clearWhere() {
        this.whereValues.clear();
        this.params.clear();
    }

    @Override
    public void clearOrder() {
        this.orderValues.clear();
    }    
    
    private String formattedSelect() {
        return format(SELECT_FRAGMENT, separatedByComma(selectValues), entityClass.getSimpleName());
    }

    private String formattedWhere() {
        return format(WHERE_FRAGMENT, separatedByAnd(whereValues));
    }

    private String formattedGroup() {
        return format(GROUP_FRAGMENT, separatedByComma(groupValues));
    }
    
    private String formattedHaving() {
        return format(HAVING_FRAGMENT, separatedByAnd(havingValues));
    }

    private String formattedOrder() {
        return format(ORDER_FRAGMENT, separatedByComma(orderValues));
    }    
    
    private TypedQuery<T> buildParameterizedQuery(String qlString) {
        return parametize(buildQuery(qlString)).setMaxResults(limit).setFirstResult(offset);
    }
    
    private <R> TypedQuery<R> buildParameterizedQuery(String qlString, Class<R> resultClass) {
        return parametize(buildQuery(qlString, resultClass)).setMaxResults(limit).setFirstResult(offset);
    }        
    
    private Query buildParameterizedQueryAlt(String qlString) {
        return parametize(buildQueryAlt(qlString)).setMaxResults(limit).setFirstResult(offset);
    }

    private <R> TypedQuery<R> parametize(TypedQuery<R> query) {        
        applyParams(query); applyHints(query); return query;
    }
    
    private Query parametize(Query query) {       
        applyParams(query); applyHints(query); return query;
    }
    
    private void applyParams(Query query) {      
        range(0, params.size()).forEach(i -> query.setParameter(i + 1, params.get(i)));                 
    }
    
    private void applyHints(Query query) {
        includesValues.forEach(i -> query.setHint("eclipselink.batch", i));
        eagerLoadsValues.forEach(i -> query.setHint("eclipselink.left-join-fetch", i));
        query.setHint("eclipselink.batch.type", "IN");
    }
    
    private String separatedBySpace(List<String> values) {
        return join(" ", values);
    }
    
    private String separatedByAnd(List<String> values) {
        return join("AND ", values);
    }  
    
    private String separatedByComma(List<String> values) {
        return join(", ", values);
    }
    
}
