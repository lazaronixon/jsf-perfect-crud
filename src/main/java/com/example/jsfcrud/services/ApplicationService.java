package com.example.jsfcrud.services;

import com.activepersistence.service.Base;

public abstract class ApplicationService<T, ID> extends Base<T, ID> {

    public abstract T find(String id);

}
