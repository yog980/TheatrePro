package com.cinema.theatrepro.movie.repo;

import com.cinema.theatrepro.movie.model.MovieSeat;
import com.cinema.theatrepro.movie.model.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieSeatRepository extends JpaRepository<MovieSeat,Long> {
    List<MovieSeat> findMovieSeatByMovieShow(MovieShow movieShow);
}
