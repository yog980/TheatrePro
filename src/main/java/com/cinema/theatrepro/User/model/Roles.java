package com.cinema.theatrepro.User.model;

import com.cinema.theatrepro.shared.abstracts.AbstractEntity;
import com.cinema.theatrepro.shared.enums.RoleName;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Entity
public class Roles extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    @NaturalId
    private RoleName name;
    private Status status;
}
