package com.cinema.theatrepro.movie.model;

import com.cinema.theatrepro.shared.abstracts.AbstractEntity;
import com.cinema.theatrepro.shared.enums.ShowShift;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.Date;

@Entity
@Getter
@Setter
public class MovieShow extends AbstractEntity {
    private ShowShift shift;
    private Date startDate;
    private double discountPercentage;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    private Status status;

}
