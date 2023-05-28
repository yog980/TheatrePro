package com.cinema.theatrepro.movie.dto;

import com.cinema.theatrepro.movie.model.Movie;
import com.cinema.theatrepro.movie.model.Theatre;
import com.cinema.theatrepro.shared.enums.ShowShift;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class MovieShowDto {
    private ShowShift shift;
    private String startDate;
    private double discountPercentage;
    private Long movieId;
    private Long theatreId;
    private Status status;
}
