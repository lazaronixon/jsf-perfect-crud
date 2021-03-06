package com.example.jsfcrud.models;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class ApplicationRecord<ID> {

    @Transient private boolean newRecord = true;

    @Transient private boolean destroyed = false;

    public abstract ID getId();

    public abstract void setId(ID value);

    public boolean isNewRecord() {
        return newRecord;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isPersisted() {
        return !(newRecord || destroyed);
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        // If you want timestamp it should be overridden on child.
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        // If you want timestamp it should be overridden on child.
    }

    @PrePersist
    private void prePersist() {
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    private void preUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

    @PostPersist
    private void postPersist() {
        newRecord = false;
    }

    @PostUpdate
    private void postUpdate() {
        newRecord = false;
    }

    @PostRemove
    private void postRemove() {
        destroyed = true;
    }

    @PostLoad
    private void postLoad() {
        newRecord = false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;

        return Objects.equals(getId(), ((ApplicationRecord) other).getId());
    }

}
