package com.cinema.theatrepro.movie.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookingDto {
    private List<Long> seatIds;
    private Long showId;
}
