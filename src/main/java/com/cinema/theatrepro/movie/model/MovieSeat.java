package com.cinema.theatrepro.movie.model;

import com.cinema.theatrepro.shared.abstracts.AbstractEntity;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class MovieSeat extends AbstractEntity {
    private boolean isBooked;
    private boolean isDisabled;
    private Status status;
    @ManyToOne
    @JoinColumn(name = "show_id")
    private MovieShow movieShow;
}
