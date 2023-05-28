package com.cinema.theatrepro.movie.model;

import com.cinema.theatrepro.shared.abstracts.AbstractEntity;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Theatre extends AbstractEntity {
    private String title;
    private int seats;
    private Status status;
}
