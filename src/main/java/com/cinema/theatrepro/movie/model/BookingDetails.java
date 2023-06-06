package com.cinema.theatrepro.movie.model;

import com.cinema.theatrepro.User.model.User;
import com.cinema.theatrepro.shared.abstracts.AbstractEntity;
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
public class BookingDetails extends AbstractEntity {
    private Date bookingDate;
    private Status status;
    private BookingStatus bookingStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "show_id")
    MovieShow movieShow;
}
