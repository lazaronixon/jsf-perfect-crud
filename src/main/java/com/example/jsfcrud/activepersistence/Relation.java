package com.example.jsfcrud.activepersistence;

import com.example.jsfcrud.activepersistence.relation.Calculation;
import com.example.jsfcrud.activepersistence.relation.FinderMethods;
import com.example.jsfcrud.activepersistence.relation.QueryMethods;
import com.example.jsfcrud.services.ApplicationService;
import static java.lang.String.format;
import java.util.List;
import static java.util.stream.IntStream.range;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Relation<T> implements FinderMethods<T>, QueryMethods<T>, QueryBuilders<T>, Calculation<T> {
    
    private final static String SELECT_FRAGMENT = "SELECT %s FROM %s this";
    private final static String WHERE_FRAGMENT  = "WHERE %s";
    private final static String GROUP_FRAGMENT  = "GROUP BY %s";
    private final static String HAVING_FRAGMENT = "HAVING %s";
    private final static String ORDER_FRAGMENT  = "ORDER BY %s";       

    private final EntityManager entityManager;

    private final Class entityClass;

    private String select = "this";      

    private String where;

    private String group;
    
    private String having;

    private String order;

    private String joins;      

    private int limit  = 0;

    private int offset = 0;
    
    private boolean distinct = false;
    
    private Object[] whereParams  = new Object[0];
    
    private Object[] havingParams = new Object[0];

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
        if (joins != null)  qlString.append(" ").append(joins);
        if (where != null)  qlString.append(" ").append(formattedWhere());
        if (group != null)  qlString.append(" ").append(formattedGroup());
        if (having != null) qlString.append(" ").append(formattedHaving());
        if (order != null)  qlString.append(" ").append(formattedOrder());
        return qlString.toString();
    }
    
    private String formattedSelect() {
        return format(SELECT_FRAGMENT, select, entityClass.getSimpleName());
    }

    private String formattedWhere() {
        return format(WHERE_FRAGMENT, where);
    }

    private String formattedGroup() {
        return format(GROUP_FRAGMENT, group);
    }
    
    private String formattedHaving() {
        return format(HAVING_FRAGMENT, having);
    }    

    private String formattedOrder() {
        return format(ORDER_FRAGMENT, order);
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
        range(0, params().length).forEach(i -> query.setParameter(i + 1, params()[i])); return query;
    }
    
    private Query parametize(Query query) {
        range(0, params().length).forEach(i -> query.setParameter(i + 1, params()[i])); return query;
    }   
    
    private Object[] params() {
        return Stream.of(whereParams, havingParams).flatMap(Stream::of).toArray(String[]::new);
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
    public void setSelect(String select) {
        this.select = select;
    }   
    
    @Override
    public void setJoins(String joins) {
        this.joins = joins;
    }
    
    @Override
    public void setWhere(String where) {
        this.where = where;
    }
    
    @Override
    public void setWhereParams(Object[] params) {
        this.whereParams = params;
    }      
    
    @Override
    public void setGroup(String group) {
        this.group = group;
    }
    
    @Override
    public void setHaving(String having) {
        this.having = having;
    }    

    @Override
    public void setHavingParams(Object[] params) {
        this.havingParams = params;
    }
    
    @Override
    public String getOrder() {
        return order;
    }
    
    @Override
    public void setOrder(String order) {
        this.order = order;
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
        
    //</editor-fold>

}
