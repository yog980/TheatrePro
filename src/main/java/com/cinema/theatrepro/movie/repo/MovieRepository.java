package com.cinema.theatrepro.movie.repo;

import com.cinema.theatrepro.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> getAllByIsTrending(Boolean isTrending);
}
