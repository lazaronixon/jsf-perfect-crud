package com.example.jsfcrud.models;

import java.util.Objects;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ApplicationRecord<I> {

    public abstract I getId();

    public abstract void setId(I id);

    public boolean isNewRecord() {
        return getId() == null;
    }

    public boolean isPersisted() {
        return getId() != null;
    }

    public String getIndexPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getNewPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getEditPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int hashCode() {
        return (getId() != null) ? Objects.hash(getId()) : super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return (getId() != null && getClass().isInstance(other) && other.getClass().isInstance(this))
                ? getId().equals(((ApplicationRecord<?>) other).getId())
                : (other == this);
    }

}
