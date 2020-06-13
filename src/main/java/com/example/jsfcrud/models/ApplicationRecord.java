package com.example.jsfcrud.models;

import com.activepersistence.model.Base;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ApplicationRecord<ID> extends Base<ID>{

}
