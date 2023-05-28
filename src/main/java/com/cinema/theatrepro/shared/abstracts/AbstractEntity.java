package com.cinema.theatrepro.shared.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AbstractEntity extends AbstractPersist<Long> {

    @Version
    private Long version = 1L;

    @JsonIgnore
    @CreatedDate
    @Getter
    @Setter
    private Date createdDate = new Date();

    @JsonIgnore
    @LastModifiedDate
    @Getter
    @Setter
    private Date lastModifiedDate;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return super.isNew();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }


    @PrePersist
    public void prePersist() {
        this.lastModifiedDate = new Date();
    }
}
