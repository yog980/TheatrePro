package com.cinema.theatrepro.movie.dto;

import com.cinema.theatrepro.shared.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponse {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private String image;
    private String releaseDate;
    private String duration;
    private boolean isTrending;
    private String bannerImage;
}
