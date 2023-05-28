package com.cinema.theatrepro.movie.model;


import com.cinema.theatrepro.shared.abstracts.AbstractEntity;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.Date;

@Entity
@Getter
@Setter
public class Movie extends AbstractEntity {
    private String title;

    @Column(length = 2000)
    private String description;

    @Lob
    private byte[] image;

    private Status status;

    private double revenue;

    private Date releaseDate;

    private String duration;

}
