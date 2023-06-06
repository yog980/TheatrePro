package com.cinema.theatrepro.movie.repo;

import com.cinema.theatrepro.movie.model.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {
}
