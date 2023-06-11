package com.cinema.theatrepro.movie.repo;

import com.cinema.theatrepro.movie.dto.BookingResource;
import com.cinema.theatrepro.movie.model.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {
    @Query(value = "SELECT bd.id                                                                                    AS booking_id,\n" +
            "       bd.booking_status,\n" +
            "       bd.booking_date,\n" +
            "       ms.discount_percentage,\n" +
            "       ms.shift,\n" +
            "       ms.start_date,\n" +
            "       (SELECT m.title FROM `movie` m WHERE m.id = ms.movie_id)                                 AS movie_title,\n" +
            "       (SELECT t.title FROM `theatre` t WHERE t.id = ms.theatre_id)                             AS theatre,\n" +
            "       (SELECT COUNT(DISTINCT id) FROM `booking_detail_seats` bds WHERE bds.booking_id = bd.id) AS seats_booked\n" +
            "FROM `booking_details` bd\n" +
            "         JOIN `movie_show` ms ON (bd.show_id = ms.id)",nativeQuery = true)
    List<BookingResource> fetchAllBookingDetails();
}
