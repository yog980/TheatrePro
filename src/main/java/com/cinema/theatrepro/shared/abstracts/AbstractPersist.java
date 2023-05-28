package com.cinema.theatrepro.shared.abstracts;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractPersist<PK extends Serializable> implements Persistable<PK> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PK id;

    @Override
    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    @Transient // DATAJPA-622
    public boolean isNew() {
        return null == getId();
    }
}
