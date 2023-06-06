package com.cinema.theatrepro.movie.repo;

import com.cinema.theatrepro.movie.model.MovieSeat;
import com.cinema.theatrepro.movie.model.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieSeatRepository extends JpaRepository<MovieSeat,Long> {
    @Query("Select s  FROM MovieSeat s WHERE s.id in :ids")
    List<MovieSeat> findAllByIdsIn(List<Long> ids);
    List<MovieSeat> findMovieSeatByMovieShow(MovieShow movieShow);
}
