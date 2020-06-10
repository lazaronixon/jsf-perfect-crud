package com.example.jsfcrud.activeservice;

import com.example.jsfcrud.activeservice.relation.Calculation;
import com.example.jsfcrud.activeservice.relation.FinderMethods;
import com.example.jsfcrud.activeservice.relation.QueryMethods;
import com.example.jsfcrud.services.ApplicationService;
import static java.lang.String.format;
import java.util.List;
import static java.util.stream.IntStream.range;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Relation<T> implements FinderMethods<T>, QueryMethods<T>, QueryBuilders<T>, Calculation<T> {
    
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
    
    public T fetchOne() {
        return createParameterizedQuery(buildQlString()).getResultStream().findFirst().orElse(null);
    }    
    
    public T fetchOneAlt() {
        return createParameterizedQuery(buildQlString()).getSingleResult();
    }       
    
    @Override
    public <R> R fetchOneAs(Class<R> resultClass) {
        return createParameterizedQuery(buildQlString(), resultClass).getSingleResult();
    }      
    
    public List<T> fetch() {
        return createParameterizedQuery(buildQlString()).getResultList();
    }   
    
    @Override
    public List fetchAlt() {
        return createParameterizedQueryAlt(buildQlString()).getResultList();
    }     
    
    @Override
    public boolean fetchExists() {
        return createParameterizedQuery(buildQlString()).getResultStream().findAny().isPresent();
    }

    @Override
    public Relation<T> limit(int value) {
        this.limit = value; return this;
    }

    @Override
    public Relation<T> order(String order) {
        this.order = order; return this;
    }

    @Override
    public Relation<T> where(String conditions, Object... params) {
        this.where = conditions; this.params = params; return this;
    }

    @Override
    public String buildQlString() {
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
    
    private TypedQuery<T> createParameterizedQuery(String qlString) {
        return parametize(buildQuery(qlString)).setMaxResults(limit).setFirstResult(offset);
    }
    
    private <R> TypedQuery<R> createParameterizedQuery(String qlString, Class<R> resultClass) {
        return parametize(buildQuery(qlString, resultClass)).setMaxResults(limit).setFirstResult(offset);
    }        
    
    private Query createParameterizedQueryAlt(String qlString) {
        return parametize(buildQueryAlt(qlString)).setMaxResults(limit).setFirstResult(offset);
    }

    private <R> TypedQuery<R> parametize(TypedQuery<R> query) {
        range(0, params.length).forEach(i -> query.setParameter(i + 1, params[i])); return query;
    }
    
    private Query parametize(Query query) {
        range(0, params.length).forEach(i -> query.setParameter(i + 1, params[i])); return query;
    }   
    
    //<editor-fold defaultstate="collapsed" desc="Get/Set">
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }    
    
    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }
    
    @Override
    public String getOrder() {
        return order;
    }
    
    @Override
    public void setFields(String fields) {
        this.fields = fields;
    }
    
    @Override
    public void setOffset(int value) {
        this.offset = value;
    }
    
    @Override
    public void setJoins(String value) {
        this.joins = value;
    }
    
    @Override
    public void setGroup(String group) {
        this.group = group;
    }
    //</editor-fold>

}