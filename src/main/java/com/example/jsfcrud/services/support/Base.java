package com.example.jsfcrud.services.support;

public interface Base<T> extends Persistence<T>, FinderMethods<T>, QueryMethods<T>, Calculations<T> {

}
