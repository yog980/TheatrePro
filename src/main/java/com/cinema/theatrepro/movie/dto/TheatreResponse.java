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
public class TheatreResponse {
    private String title;
    private Integer seats;
    private Status status;
}
