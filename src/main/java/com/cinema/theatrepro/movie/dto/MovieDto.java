package com.cinema.theatrepro.movie.dto;

import com.cinema.theatrepro.shared.enums.Status;
import lombok.Data;

@Data
public class MovieDto {
    private String title;
    private String description;
    private Status status;
    private byte[] image;
    private String releaseDate;
    private String duration;
}
