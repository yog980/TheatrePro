package com.cinema.theatrepro.movie.repo;

import com.cinema.theatrepro.movie.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
}
