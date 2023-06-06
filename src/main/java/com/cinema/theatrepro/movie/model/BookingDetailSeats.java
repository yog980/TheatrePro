package com.cinema.theatrepro.movie.model;

import com.cinema.theatrepro.shared.abstracts.AbstractEntity;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Getter
@Setter
public class BookingDetailSeats extends AbstractEntity {
    private BookingStatus bookingStatus;
    private Status status;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    BookingDetails bookingDetails;
    @OneToOne
    @JoinColumn(name = "booking_seat_id")
    MovieSeat movieSeat;
    private String seatCancelReason;
    private Date seatCanceledDate;
}
