package com.cinema.theatrepro.movie.dto;

import com.cinema.theatrepro.shared.enums.ShowShift;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieShowResponse {
    private Long id;
    private ShowShift shift;
    private String startDate;
    private String startTime;
    private double discountPercentage;
    private double pricePerTicket;
    private MovieMiniResource movie;
    private TheatreResponse theatre;
    private Status status;
}
