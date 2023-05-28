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
public class MovieMiniResource {
    private Long movieId;
    private String movieTitle;
    private Status movieStatus;
   }
