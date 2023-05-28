package com.cinema.theatrepro.movie.repo;

import com.cinema.theatrepro.movie.model.Movie;
import com.cinema.theatrepro.movie.model.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieShowRepository extends JpaRepository<MovieShow,Long> {
    List<MovieShow> findAllByMovie(Movie movie);
}
