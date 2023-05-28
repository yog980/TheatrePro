package com.cinema.theatrepro.movie.dto;

import com.cinema.theatrepro.shared.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieSeatResponse {
    private Long seatId;
    private boolean isBooked;
    private boolean isDisabled;
    private Status status;
    private Long movieShowId;
}
