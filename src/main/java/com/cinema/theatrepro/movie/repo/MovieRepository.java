package com.cinema.theatrepro.movie.repo;

import com.cinema.theatrepro.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
