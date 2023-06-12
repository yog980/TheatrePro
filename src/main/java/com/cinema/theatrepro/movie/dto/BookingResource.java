package com.cinema.theatrepro.movie.dto;

import com.cinema.theatrepro.movie.model.BookingStatus;
import com.cinema.theatrepro.shared.enums.ShowShift;
import lombok.*;

import java.util.Date;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class BookingResource {
        private Long bookingId;
        private BookingStatus bookingStatus;
        private Date bookingDate;
        private double discountPercentage;
        private ShowShift shift;
        private Date startDate;
        private String movieTitle;
        private String theatre;
        private Long seatsBooked;
    }
